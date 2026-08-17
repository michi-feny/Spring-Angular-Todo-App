package ibee.webapp.todo_app.core.service.person.related.contact.phone;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.phone.PersonPhoneNumberRepositroy;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
public class PersonPhoneNumberServiceImpl
        extends PersonRelatedServiceImpl<PersonPhoneNumber, PersonPhoneNumberId> {

    public PersonPhoneNumberServiceImpl(PersonPhoneNumberRepositroy repository) {
        super(repository);
    }
}
