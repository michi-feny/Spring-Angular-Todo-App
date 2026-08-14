package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneUiId;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;

@Mapper(config = MapStructConfig.class)
public interface PersonPhoneReferenceMapper {

    @Mapping(
        target = "phoneNumberId",
        source = "phoneNumberId"
    )
    PersonPhoneUiId toModel(PersonPhoneNumberId id);
}
