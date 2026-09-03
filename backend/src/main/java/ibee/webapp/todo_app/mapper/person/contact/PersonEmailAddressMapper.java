package ibee.webapp.todo_app.mapper.person.contact;



import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.contact.mail.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.features.person.related.contact.PersonAddressDto;
import ibee.webapp.todo_app.mapper.EmailAddressMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonEmailAddressReferenceMapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;



@Mapper(
    config = MapStructConfig.class,
    uses = {  
        EmailAddressMapper.class,
        PersonEmailAddressReferenceMapper.class
    }
)
public interface PersonEmailAddressMapper extends BaseMapper<PersonEmailAddressDto, PersonEmailAddress> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "emailAddress", source = "emailAddress")
    @Mapping(target = "mainEmail", source = "mainEmail")
    PersonEmailAddressDto toDto(PersonEmailAddress entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "emailAddress", source = "emailAddress")
    @Mapping(target = "mainEmail", source = "mainEmail")
    PersonEmailAddress toEntity(PersonEmailAddressDto dto);

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "person", ignore = true) // Target IS the Entity, so we MUST ignore it here
    @Mapping(target = "emailAddress", ignore = true)
    @Mapping(target = "mainEmail", source = "mainEmail", defaultValue = "false")
    void updateEntityFromDto(PersonEmailAddressDto dto, @MappingTarget PersonEmailAddress entity);

       // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true) // Target IS the Entity
    @Mapping(target = "emailAddress", ignore = true)
    void updateEntityFromEntity(PersonEmailAddress sourceUpdates, @MappingTarget PersonEmailAddress dbEntity);

    
}