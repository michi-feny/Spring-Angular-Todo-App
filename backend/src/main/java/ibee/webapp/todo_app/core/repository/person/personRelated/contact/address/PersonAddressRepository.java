package ibee.webapp.todo_app.core.repository.person.personRelated.contact.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonAddressRepository 
    extends PersonRelatedRepository
        <PersonAddress, PersonAddressId>{

    @EntityGraph(attributePaths = {
        "address",
        "address.country"
    })
    Optional<PersonAddress> findWithDetailsById(
        PersonAddressId id
    );

    @EntityGraph(attributePaths = {"address", "address.country"}) 
    List<PersonAddress> findByPersonId(Long personId);

    @Modifying
    @Query("""
        UPDATE PersonAddress pa 
        SET pa.mainAddress = false 
        WHERE pa.person.id = :personId 
          AND pa.id.addressId != :excludeAddressId
    """)
    void resetOtherMainAddresses(
        @Param("personId") Long personId, 
        @Param("excludeAddressId") Long excludeAddressId
    );

    boolean existsByPerson_IdAndMainAddressTrue(Long personId);

    Optional<PersonAddress> findByPersonIdAndMainAddressTrue(Long personId);

    
}
