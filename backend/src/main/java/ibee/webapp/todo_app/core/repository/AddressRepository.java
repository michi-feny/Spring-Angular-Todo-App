package ibee.webapp.todo_app.core.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@Repository
public interface AddressRepository 
    extends MyFacadeBaseCrudRepository<Address, Long>{
    //List<Address> findByStreetAndCity(String street, String city);

    Optional<Address> findByStreetAndHouseNumberAndZipCodeAndCityAndCountryId(
        String street, 
        String houseNumber, 
        String zipCode, 
        String city, 
        Long countryId
    );

    List<Address> findByZipCodeIn(Collection<String> zipCodes);



}
