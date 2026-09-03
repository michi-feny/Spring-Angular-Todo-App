package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonPhoneNumberReferenceMapper 
    extends BaseMapper<PersonPhoneNumberDtoId, PersonPhoneNumberId> {

            // DTO ID -> Entity ID (Used when creating/saving)
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "phoneNumberId", source = "phoneNumberId")
    PersonPhoneNumberId toEntity(PersonPhoneNumberDtoId dtoId);

    // Entity ID -> DTO ID (CRITICAL for toDto / HATEOAS links)
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "phoneNumberId", source = "phoneNumberId")
    PersonPhoneNumberDtoId toDto(PersonPhoneNumberId entityId);
}
