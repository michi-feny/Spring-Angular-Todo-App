package ibee.webapp.todo_app.mapper.person.contact;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.features.person.related.contact.PersonAddressDto;
import ibee.webapp.todo_app.mapper.AddressMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        AddressMapper.class,
        PersonAddressReferenceMapper.class
    }
)
public interface PersonAddressMapper 
    extends BaseMapper<PersonAddressDto, PersonAddress> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "mainAddress", source = "mainAddress", defaultValue = "false")
    PersonAddressDto toDto(PersonAddress entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "address", source = "address")//should happen also implicit, cause of AddressMapper is present
    @Mapping(target = "mainAddress", source = "mainAddress", defaultValue = "false")
    PersonAddress toEntity(PersonAddressDto dto);

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "person", ignore = true) // Target IS the Entity, so we MUST ignore it here
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "mainAddress", source = "mainAddress", defaultValue = "false")
    void updateEntityFromDto(PersonAddressDto dto, @MappingTarget PersonAddress entity);

    // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true) // Target IS the Entity
    @Mapping(target = "address", ignore = true)
    void updateEntityFromEntity(PersonAddress sourceUpdates, @MappingTarget PersonAddress dbEntity);
}
