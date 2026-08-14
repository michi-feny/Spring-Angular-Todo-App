package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailUiId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;

@Mapper(config = MapStructConfig.class)
public interface PersonEmailReferenceMapper {

    @Mapping(
        target = "emailAddressId",
        source = "emailAddressId"
    )
    PersonEmailUiId toModel(PersonEmailAddressId id);
}
