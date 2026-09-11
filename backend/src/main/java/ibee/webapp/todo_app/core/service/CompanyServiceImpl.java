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
    @Override
    public Company create(Company entity) {
        Assert.notNull(entity, "Company entity cannot be null");

        Optional<Company> existingById = Optional.ofNullable(entity.getId())
                .flatMap(companyRepository::findById);
        if (existingById.isPresent()) {
            return existingById.get();
        }

        entity.setAddress(lookupToFetchExistingAddressOrCreteNewAddress(entity.getAddress()));

        Optional<Company> existingByNameAndCountry = findByEntityNameAndCountry(entity);
        if (existingByNameAndCountry.isPresent()) {
            return existingByNameAndCountry.get();
        }

        return super.create(entity);
    }

    private Address lookupToFetchExistingAddressOrCreteNewAddress(Address address) {
        if (address == null) {
            return null;
        }

        return Optional.ofNullable(address.getId())
                .flatMap(addressService::findByIdWithoutException)
                .orElseGet(() -> addressService.create(address));
    }

    private Optional<Company> findByEntityNameAndCountry(Company company) {
        if (company.getName() == null || company.getAddress() == null || company.getAddress().getCountry() == null) {
            return Optional.empty();
        }
        return companyRepository.findByNameAndAddress_Country(company.getName(), company.getAddress().getCountry());
    }

    private void validateNoNameAndCountryConflict(Company incomingUpdates, Long id) {
        findByEntityNameAndCountry(incomingUpdates)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                        "Another company with the name '" + incomingUpdates.getName() + "' already exists in this country."
                    );
                });
    }

    @Override
    public Company update(Company incomingUpdates, Long id) {
        Assert.notNull(incomingUpdates, "Company update payload cannot be null");
        Assert.notNull(id, "Company ID cannot be null");

        incomingUpdates.setAddress(lookupToFetchExistingAddressOrCreteNewAddress(incomingUpdates.getAddress()));

        validateNoNameAndCountryConflict(incomingUpdates, id);

        return super.update(incomingUpdates, id);
    }


    @Transactional(readOnly = true)
    public Optional<Company> findByName(String companyName) {
        return companyRepository.findByName(companyName);
    }

    @Transactional(readOnly = true)
    public Optional<Company> findByNameAndCountry(String name, Country country) {
        return companyRepository.findByNameAndAddress_Country(name, country);
    }
}
