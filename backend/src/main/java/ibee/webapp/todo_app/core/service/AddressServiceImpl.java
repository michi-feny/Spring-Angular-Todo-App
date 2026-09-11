package ibee.webapp.todo_app.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.repository.AddressRepository;
import ibee.webapp.todo_app.core.result.BusinessViolation;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.AddressMapper;

@Service
@Transactional
public class AddressServiceImpl 
    extends MyCrudBaseEntityFacedeServiceImpl<Address, Long>{

    private final AddressRepository addressRepository;
    //private final AddressMapper mapper;

    public AddressServiceImpl(AddressRepository repository, AddressMapper mapper) {
        super(repository, mapper);
        this.addressRepository = repository;
        //this.mapper = mapper;
    }    

    Optional<Address> findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
        String street, 
        String houseNumber, 
        String zipCode, 
        String city, 
        Long countryId
    ){
        return 
            addressRepository.findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
                street,houseNumber,zipCode,city,countryId);
    }

    /**
     * GLOBAL DEDUPLICATION: Overrides the base create method.
     * Ensures we NEVER insert duplicate addresses into the database.
     */
    @Override
    public Address create(Address requestedAddress) {
        assertAddressCanBeCreated(requestedAddress);

        Long countryId = requestedAddress.getCountry().getId();
        
        Optional<Address> existing = addressRepository.
            findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
                requestedAddress.getStreet(),
                requestedAddress.getHouseNumber(),
                requestedAddress.getZipCode(),
                requestedAddress.getCity(),
                countryId
        );

        if(existing.isPresent()){
            return existing.get();
        }

       

        // If it doesn't exist, let the base class actually insert it into the DB!
        return super.create(requestedAddress);
        
    }

    /*
    The Golden Rule for Callers
    Because this method might return an entirely different ID than the one requested, 
    the calling service must always capture the returned entity and update its foreign keys.

    eg PersonAddressServiceImpl is already doing exactly this! 
    It captures the returned Address and actively swaps the ID in its composite key before saving.
    
    The address already exists under another ID.
        We return that Address.
    */
    @Override
    public Address update(Address sourceUpdates, Long id) {
        Long countryId = sourceUpdates.getCountry().getId();
        
        // 1. Check if the newly requested address strings already exist in the DB
        var existingMatch = addressRepository
            .findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
                sourceUpdates.getStreet(),
                sourceUpdates.getHouseNumber(),
                sourceUpdates.getZipCode(),
                sourceUpdates.getCity(),
                countryId
        );

        /*
         * ========================================================
         * UPDATE -> DEDUPLICATION
         * ========================================================
         *
         * The address already exists under another ID.
         *
         * We return that Address.
         *
         * PersonAddressServiceImpl will then handle the fact that
         * its composite key must change.
         */
        if (existingMatch.isPresent() 
            && 
            ((existingMatch.get().getId().equals(id)) == false)) {
            return existingMatch.get();
                    
        }

        // 3. SAFE TYPO FIX: If it does not exist anywhere else, it's safe to apply the patch.
        // This will update the row, fixing the typo for everyone linked to it.
        return super.update(sourceUpdates, id);
    }

    private void assertAddressCanBeCreated(
            Address address) {

        if (address == null) {

            throw new IllegalArgumentException(
                    "Address cannot be null."
            );
        }

        if (address.getCountry() == null
                || address.getCountry().getId() == null) {

            throw new IllegalArgumentException(
                    "Address must be associated with a valid Country ID."
            );
        }
    }

    

    

}
