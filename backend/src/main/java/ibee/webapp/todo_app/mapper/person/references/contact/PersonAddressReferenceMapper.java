package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonAddressReferenceMapper
    extends BaseMapper<PersonAddressDtoId, PersonAddressId> {

        // DTO ID -> Entity ID (Used when creating/saving)
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "addressId", source = "addressId")
    PersonAddressId toEntity(PersonAddressDtoId dtoId);

    // Entity ID -> DTO ID (CRITICAL for toDto / HATEOAS links)
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "addressId", source = "addressId")
    PersonAddressDtoId toDto(PersonAddressId entityId);

    
}
