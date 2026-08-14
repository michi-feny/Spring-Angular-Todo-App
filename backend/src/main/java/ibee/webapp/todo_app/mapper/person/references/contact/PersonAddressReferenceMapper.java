package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressUiId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;

@Mapper(config = MapStructConfig.class)
public interface PersonAddressReferenceMapper {

    @Mapping(
        target = "addressId",
        source = "addressId"
    )
    PersonAddressUiId toModel(PersonAddressUiId id);
}
