package ibee.webapp.todo_app.core.service.person.related.contact.address;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.dto.person.contact.address.PersonAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;

@Service
public class PersonAddressDtoService
        extends AbstractPersonRelatedDtoService<
            PersonAddressDto,
            PersonAddress,
            PersonAddressId,
            PersonAddressDtoId> {

    public PersonAddressDtoService(
            PersonRelatedService<PersonAddress, PersonAddressId> personEntityService,
            PersonAddressMapper mapper,
            PersonAddressReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }
}
