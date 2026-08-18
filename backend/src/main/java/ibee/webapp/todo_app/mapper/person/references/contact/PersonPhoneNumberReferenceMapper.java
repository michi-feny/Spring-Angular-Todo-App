package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonPhoneNumberReferenceMapper 
    extends BaseMapper<PersonPhoneNumberDtoId, PersonPhoneNumberId> {

}
