package ibee.webapp.todo_app.core.repository.person.personRelated.contact.phone;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonPhoneNumberRepositroy 
    extends PersonRelatedRepository
        <PersonPhoneNumber, PersonPhoneNumberId>{

    @EntityGraph(attributePaths = {
        "phoneNumber"
    })
    Optional<PersonPhoneNumber> findWithOnlyPhoneNumberDetailsById(
        PersonPhoneNumberId id
    );

    @EntityGraph(attributePaths = {
        "phoneNumber",
        "phoneNumber.country"
    })
    Optional<PersonPhoneNumber> findWithDetailsById(
        PersonPhoneNumberId id
    );
}
