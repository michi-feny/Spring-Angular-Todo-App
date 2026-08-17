package ibee.webapp.todo_app.core.repository.person.personRelated.contact.mail;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonEmailAddressRepository 
    extends PersonRelatedRepository
        <PersonEmailAddress, PersonEmailAddressId>{


    @EntityGraph(attributePaths = {
        "emailAddress"
    })
    Optional<PersonEmailAddress> findWithDetailsById(
        PersonEmailAddressId id
    );
}
