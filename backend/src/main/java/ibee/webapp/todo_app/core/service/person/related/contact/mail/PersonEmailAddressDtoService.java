package ibee.webapp.todo_app.core.service.person.related.contact.mail;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.dto.person.contact.mail.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.mapper.person.contact.PersonEmailAddressMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonEmailAddressReferenceMapper;

@Service
public class PersonEmailAddressDtoService
    extends AbstractPersonRelatedDtoService<
        PersonEmailAddressDto,
        PersonEmailAddress,
        PersonEmailAddressId,
        PersonEmailAddressDtoId> {

    public PersonEmailAddressDtoService(
            PersonRelatedService<PersonEmailAddress, PersonEmailAddressId> personEntityService,
            PersonEmailAddressMapper mapper,
            PersonEmailAddressReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }

    // DTO-specific methods can be added here
}
