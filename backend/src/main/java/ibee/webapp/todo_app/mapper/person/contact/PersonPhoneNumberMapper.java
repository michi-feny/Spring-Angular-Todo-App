package ibee.webapp.todo_app.mapper.person.contact;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonPhoneNumberDto;
import ibee.webapp.todo_app.mapper.PhoneNumberMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneNumberReferenceMapper;
import ibee.webapp.todo_app.config.MapStructConfig;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    config = MapStructConfig.class,

    uses = {
        PhoneNumberMapper.class,
        PersonPhoneNumberReferenceMapper.class
    }
)
public interface PersonPhoneNumberMapper 
    extends BaseMapper<
        PersonPhoneNumberDto, PersonPhoneNumber> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target="phoneNumber", source="phoneNumber")
    @Mapping(target = "mainPhoneNumber", source = "mainPhoneNumber")
    PersonPhoneNumberDto toDto(PersonPhoneNumber entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "mainPhoneNumber", source = "mainPhoneNumber")
    PersonPhoneNumber toEntity(PersonPhoneNumberDto dto);

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "person", ignore = true) // Target IS the Entity, so we MUST ignore it here
    @Mapping(target = "phoneNumber", source ="phoneNumber")
    @Mapping(target = "mainPhoneNumber", source = "mainPhoneNumber", defaultValue = "false")
    void updateEntityFromDto(
        PersonPhoneNumberDto dto, 
        @MappingTarget PersonPhoneNumber entity
    );

    // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = 
        NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true) // Target IS the Entity
    @Mapping(target = "phoneNumber", ignore = true)
    void updateEntityFromEntity(
        PersonPhoneNumber sourceUpdates, 
        @MappingTarget PersonPhoneNumber dbEntity
    );




}
