package ibee.webapp.todo_app.core.service.person.related.contact.mail;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.mail.PersonEmailAddressRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
public class PersonEmailAddressServiceImpl
        extends PersonRelatedServiceImpl<PersonEmailAddress, PersonEmailAddressId> {

    public PersonEmailAddressServiceImpl(PersonEmailAddressRepository repository) {
        super(repository);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEmailAddress> 
        findWithDetailsById(PersonEmailAddressId id
    ) {
        return personRelatedRepository.findWithDetailsById(id);
    }
}
