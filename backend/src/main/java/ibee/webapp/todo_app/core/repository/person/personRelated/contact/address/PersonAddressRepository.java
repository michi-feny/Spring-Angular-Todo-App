package ibee.webapp.todo_app.core.repository.person.personRelated.contact.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
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
}
