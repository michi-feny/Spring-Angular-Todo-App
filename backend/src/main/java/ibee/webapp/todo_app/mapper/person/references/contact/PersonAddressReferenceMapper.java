package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonAddressReferenceMapper
    extends BaseMapper<PersonAddressDtoId, PersonAddressId> {

    @Mapping(
        target = "addressId",
        source = "addressId"
    )
    PersonAddressDtoId toModel(PersonAddressDtoId id);
}
