package ibee.webapp.todo_app.core.service.person.related.contact.mail;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.mail.PersonMailAddressRepository;
import ibee.webapp.todo_app.core.service.baseService.person.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonEmailAddressServiceImpl
        extends PersonRelatedServiceImpl<
                PersonEmailAddress,
                PersonEmailAddressId>
        implements PersonEmailAddressService {

    private final PersonMailAddressRepository repository;

    public PersonEmailAddressServiceImpl(
            PersonMailAddressRepository repository
    ) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEmailAddress> 
        findWithDetailsById(PersonEmailAddressId id
    ) {
        return repository.findWithDetailsById(id);
    }
}
