package ibee.webapp.todo_app.core.service.person.related.contact.address;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.address.PersonAddressRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;

@Service
@Transactional
public class PersonAddressService
        extends PersonRelatedServiceImpl<
                PersonAddress,
                PersonAddressId> {

    private final PersonAddressMapper mapper;

    public PersonAddressService(
            PersonAddressRepository repository,
            PersonAddressMapper mapper) {

        super(repository);

        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PersonAddressInfo> findInfoByPersonId(
            Long personId) {

        return findByPersonId(personId)
                .stream()
                .map(mapper::toInfo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PersonAddressInfo> findInfoById(
            PersonAddressId id) {

        return findWithDetailsById(id)
                .map(mapper::toInfo);
    }

    public PersonAddress save(
            PersonAddressForm form) {

        return save(mapper.toEntity(form));
    }

    public PersonAddress update(
            PersonAddressForm form) {

        return save(mapper.toEntity(form));
    }
}
