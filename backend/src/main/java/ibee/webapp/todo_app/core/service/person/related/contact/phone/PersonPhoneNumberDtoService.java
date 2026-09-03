package ibee.webapp.todo_app.core.service.person.related.contact.phone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.dto.person.contact.mail.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.dto.person.contact.phone.PersonPhoneNumberDto;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.mapper.person.contact.PersonPhoneNumberMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneNumberReferenceMapper;


@Service
@Transactional
public class PersonPhoneNumberDtoService
    extends AbstractPersonRelatedDtoService<
        PersonPhoneNumberDto,
        PersonPhoneNumber,
        PersonPhoneNumberId,
        PersonPhoneNumberDtoId> 
    implements PersonRelatedDtoService<
        PersonPhoneNumberDto,
        PersonPhoneNumber,
        PersonPhoneNumberId,
        PersonPhoneNumberDtoId> {

    public PersonPhoneNumberDtoService(
            PersonRelatedService<PersonPhoneNumber, PersonPhoneNumberId> personEntityService,
            PersonPhoneNumberMapper mapper,
            PersonPhoneNumberReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }

    // add any DTO-specific business methods here
}
