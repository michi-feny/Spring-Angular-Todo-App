package ibee.webapp.todo_app.mapper.person.contact;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.CountryMapper;
import ibee.webapp.todo_app.mapper.CountryTranslationMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonCountryReferenceMapper;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonCountryDto;

@Mapper(
    config = MapStructConfig.class,
    uses = { 
            CountryMapper.class,
            CountryTranslationMapper.class,
            PersonCountryReferenceMapper.class
     }
)
public interface PersonCountryMapper 
    extends BaseMapper<PersonCountryDto, PersonCountry> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "mainCountry", source = "mainCountry") // map referential object
    PersonCountryDto toDto(PersonCountry entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "mainCountry", source = "mainCountry") 
    PersonCountry toEntity(PersonCountryDto dto);

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "person", ignore = true) // Target IS the Entity, so we MUST ignore it here
    @Mapping(target = "mainCountry", source = "mainCountry", defaultValue = "false")
    @Mapping(target = "country", ignore = true) // <-- Add this to prevent mapping/instantiating Country on update
    void updateEntityFromDto(PersonCountryDto dto, @MappingTarget PersonCountry entity);

    // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true) // Target IS the Entity
    @Mapping(target = "country", ignore = true)
    void updateEntityFromEntity(PersonCountry sourceUpdates, @MappingTarget PersonCountry dbEntity);

}
