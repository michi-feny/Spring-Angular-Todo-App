package ibee.webapp.todo_app.core.service.person.related.contact.address;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.address.PersonAddressRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonAddressServiceImpl
        extends PersonRelatedServiceImpl<PersonAddress, PersonAddressId> {

    public PersonAddressServiceImpl(PersonAddressRepository repository) {
        super(repository);
    }

}
