package ibee.webapp.todo_app.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.repository.AddressRepository;
import ibee.webapp.todo_app.core.service.baseService.newApproach.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.AddressMapper;

@Service
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
        // 1. Ensure the country ID is present from the reference stub
        if (requestedAddress.getCountry() == null 
            || requestedAddress.getCountry().getId() == null) {
            throw new IllegalArgumentException("Address must be associated with a valid Country ID.");
        }

        Long countryId = requestedAddress.getCountry().getId();
        
        return addressRepository.findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
            requestedAddress.getStreet(),
            requestedAddress.getHouseNumber(),
            requestedAddress.getZipCode(),
            requestedAddress.getCity(),
            countryId
        ).orElseGet(() -> {
            // If it doesn't exist, let the base class actually insert it into the DB!
            return super.create(requestedAddress);
        });
    }

    /*
    The Golden Rule for Callers
    Because this method might return an entirely different ID than the one requested, 
    the calling service must always capture the returned entity and update its foreign keys.

    eg PersonAddressServiceImpl is already doing exactly this! 
    It captures the returned Address and actively swaps the ID in its composite key before saving.
    */
    @Override
    public Address update(Address sourceUpdates, Long id) {
        Long countryId = sourceUpdates.getCountry().getId();
        
        // 1. Check if the newly requested address strings already exist in the DB
        var existingMatch = addressRepository.findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
            sourceUpdates.getStreet(),
            sourceUpdates.getHouseNumber(),
            sourceUpdates.getZipCode(),
            sourceUpdates.getCity(),
            countryId
        );

        // 2. THE SELF-FIX: If it exists under a DIFFERENT ID, we do NOT update.
        // We quietly return the existing matched row to force deduplication!
        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }

        // 3. SAFE TYPO FIX: If it does not exist anywhere else, it's safe to apply the patch.
        // This will update the row, fixing the typo for everyone linked to it.
        return super.update(sourceUpdates, id);
    }

    

    

}
