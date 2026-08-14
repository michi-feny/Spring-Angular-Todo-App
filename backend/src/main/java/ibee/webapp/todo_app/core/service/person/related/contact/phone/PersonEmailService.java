package ibee.webapp.todo_app.core.service.person.related.contact.phone;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.phone.PersonPhoneNumberRepositroy;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonEmailService
        extends PersonRelatedServiceImpl<
                PersonEmailAdress,
                PersonEmailAddressId> {

    private final PersonEmailMapper mapper;

    public PersonEmailService(
            PersonEmailAdressRepository repository,
            PersonEmailMapper mapper) {

        super(repository);

        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PersonEmailInfo> findInfoByPersonId(
            Long personId) {

        return findByPersonId(personId)
                .stream()
                .map(mapper::toInfo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PersonEmailInfo> findInfoById(
            PersonEmailAddressId id) {

        return findWithDetailsById(id)
                .map(mapper::toInfo);
    }

    public PersonEmailAdress save(
            PersonEmailForm form) {

        return save(mapper.toEntity(form));
    }

    public PersonEmailAdress update(
            PersonEmailForm form) {

        return save(mapper.toEntity(form));
    }
}
