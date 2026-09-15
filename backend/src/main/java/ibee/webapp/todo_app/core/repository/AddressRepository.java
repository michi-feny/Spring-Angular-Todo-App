package ibee.webapp.todo_app.core.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    // NEW: Fetch the Address AND its Country in a single query
    @EntityGraph(attributePaths = {"country"})
    Optional<Address> findWithAssociationsById(Long id);


    /**
     * GARBAGE COLLECTION: Deletes any address not linked to a Company.
     * 
     * IMPORTANT: If you have other entities (like Person or School) that use Addresses,
     * you MUST add them to this query using AND NOT EXISTS (...).
     */
    @Modifying
    @Query("DELETE FROM Address a WHERE NOT EXISTS (SELECT 1 FROM Company c WHERE c.address = a)")
    int deleteOrphanedAddresses();

}
