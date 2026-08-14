package ibee.webapp.todo_app.mapper.person.contact;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.contact.address.PersonAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.mapper.AddressMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        AddressMapper.class
    }
)
public interface PersonAddressMapper 
    extends BaseMapper<PersonAddressDto, PersonAddress> {

    @Override
    @Mapping(target = "id.personId", source = "id.personId")
    @Mapping(target = "id.addressId", source = "id.addressId")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "mainAddress", source = "mainAddress")
    PersonAddressDto toDto(PersonAddress entity);

    @Override
    @Mapping(target = "id.personId", source = "id.personId")
    @Mapping(target = "id.addressId", source = "id.addressId")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "address.id", source = "id.addressId")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "mainAddress", source = "mainAddress")
    PersonAddress toEntity(PersonAddressDto dto);

    @Override
    default List<PersonAddressDto> toDtoList(List<PersonAddress> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).toList();
    }
    
    @Override
    default List<PersonAddress> toEntityList(List<PersonAddressDto> dtos) {
        return dtos == null ? null : dtos.stream().map(this::toEntity).toList();
    }

    // UI id helpers (entity -> ui id and dto -> ui id)
    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "addressId", source = "id.addressId")
    PersonAddressDtoId toUiId(PersonAddress entity);

    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "addressId", source = "id.addressId")
    PersonAddressDtoId toUiId(PersonAddressDto dto);

    default List<PersonAddressDtoId> toUiIdListFromEntities(List<PersonAddress> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toUiId).toList();
    }

    default List<PersonAddressDtoId> toUiIdListFromDtos(List<PersonAddressDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toUiId).toList();
    }
    
    

}
