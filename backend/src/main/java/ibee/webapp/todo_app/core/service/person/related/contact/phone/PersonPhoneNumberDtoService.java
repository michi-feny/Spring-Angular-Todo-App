package ibee.webapp.todo_app.core.service.person.related.contact.phone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.dto.person.contact.phone.PersonPhoneNumberDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.mapper.person.contact.PersonPhoneNumberMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneReferenceMapper;


@Service
public class PersonPhoneNumberDtoService
    extends AbstractPersonRelatedDtoService<
        PersonPhoneNumberDto,
        PersonPhoneNumber,
        PersonPhoneNumberId,
        PersonPhoneNumberDtoId> {

    public PersonPhoneNumberDtoService(
            PersonRelatedService<PersonPhoneNumber, PersonPhoneNumberId> personEntityService,
            PersonPhoneNumberMapper mapper,
            PersonPhoneReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }

    // add any DTO-specific business methods here
}
