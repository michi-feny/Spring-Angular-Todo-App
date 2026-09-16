package ibee.webapp.todo_app.core.service;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.Company;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.repository.CompanyRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.CompanyMapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class CompanyServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<Company, Long> {

    @Autowired
    private AddressServiceImpl addressService;

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(
            CompanyRepository repository,
            CompanyMapper entityMapper) {
        super(repository, entityMapper);
        this.companyRepository = repository;
    }
    // @Override
    // public Company create(Company entity) {
    //     Assert.notNull(entity, "Company entity cannot be null");

    //     Optional<Company> existingById = Optional.ofNullable(entity.getId())
    //             .flatMap(companyRepository::findById);
    //     if (existingById.isPresent()) {
    //         return existingById.get();
    //     }

    //     entity.setAddress(lookupToFetchExistingAddressOrCreteNewAddress(entity.getAddress()));

    //     Optional<Company> existingByNameAndCountry = findByEntityNameAndAndlegalFormAndCountry(entity);
    //     if (existingByNameAndCountry.isPresent()) {
    //         return existingByNameAndCountry.get();
    //     }

    //     return super.create(entity);
    // }

    /* ==============================================================================
     * 1. DIRECT API ENDPOINTS
     * ============================================================================== */

    @Override
    public Company create(Company entity) {
        assertCompanyCanBeProcessed(entity);
        return lookupToFetchExistingCompanyOrCreateNewCompany(entity);
    }

    @Override
    public Company update(Company incomingUpdates, Long id) {
        assertCompanyCanBeProcessed(incomingUpdates);
        Assert.notNull(id, "Company ID cannot be null");

        // 1. Resolve Address
        var resolvedAddress = addressService.lookupToFetchExistingAddressOrCreateNewAddress(incomingUpdates.getAddress());
        incomingUpdates.setAddress(resolvedAddress);

        // 2. GRACEFUL DEDUPLICATION
        // If a duplicate already exists under another ID, return it silently.
        // The old ID (zombie) can be cleaned up later by a periodic database job!
        Optional<Company> duplicateCheck = findByEntityBusinessKeys(incomingUpdates);
        if (duplicateCheck.isPresent() && !duplicateCheck.get().getId().equals(id)) {
            return duplicateCheck.get(); 
        }

        // 3. Actually update the row if it's safe
        return super.update(incomingUpdates, id);
    }

    // private Address lookupToFetchExistingAddressOrCreteNewAddress(Address address) {
    //     if (address == null) {
    //         return null;
    //     }

    //     //here we need to add also first a search for Addresses
    //     //existingAddressFromItsValues:= findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId

    //     //existingAddressFromItsId:= addressService::findByIdWithoutException

    //     //if(existingAddressFromItsId!= null){
    //     //do equalsOnlyValues with lambock, by overriting the usual equals and ignore the ids
    //     //      if(existingAddressFromItsId.equalsOnlyValues(existingAddressFromItsValues)){
    //     //              //return the existingAddressFromItsId
    //     //      }else{
    //     //              return do a create with the values of existingAddressFromItsId
    //     //      }
    //     //}
    //     //else{
    //     //         if(existingAddressFromItsValues == null)
    //     //              return do a create with values of address
    //     //          else
    //     //              return existingAddressFromItsValues
    //     //  }

    //     return Optional.ofNullable(address.getId())
    //             .flatMap(addressService::findByIdWithoutException)
    //             .orElseGet(() -> addressService.create(address));
    // }
    /* ==============================================================================
     * 2. NESTED SMART RESOLVER
     * ============================================================================== */

    public Company lookupToFetchExistingCompanyOrCreateNewCompany(Company company) {
        if (company == null) {
            return null;
        }

        assertCompanyCanBeProcessed(company);

        // 1. Resolve Address
        var resolvedAddress = addressService.lookupToFetchExistingAddressOrCreateNewAddress(company.getAddress());
        company.setAddress(resolvedAddress);

        // 2. Fetch Context
        Optional<Company> existingCompanyFromItsValues = findByEntityBusinessKeys(company);
        Optional<Company> existingCompanyFromItsId = findCompanyByIdWithoutException(company.getId());

        // 3. The "Double-Fork" Logic
        if (existingCompanyFromItsId.isPresent()) {
            if (existingCompanyFromItsId.get().hasEqualValuesAs(company)) {
                return existingCompanyFromItsId.get(); 
            } else {
                return forkAndReuseOrCreateCompany(company, existingCompanyFromItsValues);
            }
        } else {
            return existingCompanyFromItsValues.orElseGet(() -> super.create(company));
        }
    }
    /* ==============================================================================
     * 3. HELPER METHODS 
     * ============================================================================== */

    private void assertCompanyCanBeProcessed(Company company) {
        Assert.notNull(company, "Company entity cannot be null");
        if (company.getName() == null || company.getLegalForm() == null || 
            company.getAddress() == null || company.getAddress().getCountry() == null || 
            company.getAddress().getCountry().getId() == null) {
            throw new IllegalArgumentException("Company must have a valid Name, Legal Form, Address, and Country ID.");
        }
    }

    /**
     * Attempts to delete a company (e.g., an old typo). 
     * If other users are still using it, the database safely aborts the deletion.
     */
    public void deleteIfOrphaned(Long companyId) {
        if (companyId != null) {
            companyRepository.deleteIfOrphaned(companyId);
        }
    }

    private Optional<Company> findCompanyByIdWithoutException(Long id) {
        return Optional.ofNullable(id).flatMap(companyRepository::findWithDetailsById);
    }

    private Company forkAndReuseOrCreateCompany(Company company, Optional<Company> existingFromValues) {
        company.setId(null); 
        return existingFromValues.orElseGet(() -> super.create(company));
    }

    private Optional<Company> findByEntityBusinessKeys(Company company) {
        
        return companyRepository.findByNameAndLegalFormAndAddress_CountryId(
                company.getName(), 
                company.getLegalForm(), 
                company.getAddress().getCountry().getId()
        );
    }

    // private Optional<Company> findByEntityNameAndAndlegalFormAndCountry(Company company) {
    //     if (
    //         company.getName() == null 
    //         || company.getAddress() == null 
    //         || company.getAddress().getCountry() == null
    //         || company.getLegalForm() == null
    //     ) {
    //         return Optional.empty();
    //     }
    //     return findByNameAndCountryAndLegalForm(
    //         company.getName(), 
    //         company.getLegalForm(),
    //         company.getAddress().getCountry());
    // }

    // //maybe all that is a fuck up staff without any sence
    // private void validateNoNameAndCountryConflict(Company incomingUpdates, Long id) {
    //     findByNameAndCountryAndLegalForm(incomingUpdates)
    //             .filter(existing -> !existing.getId().equals(id))
    //             .ifPresent(existing -> {
    //                 throw new IllegalArgumentException(
    //                     "Another company with the name '" + incomingUpdates.getName() + "' already exists in this country."
    //                 );

    //             });
    // }

    // @Override
    // public Company update(Company incomingUpdates, Long id) {
    //     Assert.notNull(incomingUpdates, "Company update payload cannot be null");
    //     Assert.notNull(id, "Company ID cannot be null");

    //     incomingUpdates.setAddress(lookupToFetchExistingAddressOrCreteNewAddress(incomingUpdates.getAddress()));

    //     validateNoNameAndCountryConflict(incomingUpdates, id);

    //     return super.update(incomingUpdates, id);
    // }


    // @Transactional(readOnly = true)
    // public Optional<Company> findByName(String companyName) {
    //     return companyRepository.findByName(companyName);
    // }

    @Transactional(readOnly = true)
    public Optional<Company> findByNameAndCountryAndLegalForm(String name, String legalForm, Country country) {
        //TODO validate input
        return companyRepository.findByNameAndLegalFormAndAddress_Country(name, legalForm,country);
    }

    @Transactional(readOnly = true)
    public Optional<Company> findByNameAndCountryAndLegalForm(Company searchedCompanyValues) {
        //TODO validate company
        
        String name = searchedCompanyValues.getName();
        String legalForm = searchedCompanyValues.getLegalForm();
        Country country = searchedCompanyValues.getAddress().getCountry();
        
        return findByNameAndCountryAndLegalForm(name, legalForm,country);
    }
}
