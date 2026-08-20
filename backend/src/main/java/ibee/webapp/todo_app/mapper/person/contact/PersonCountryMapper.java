package ibee.webapp.todo_app.mapper.person.contact;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.CountryMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonCountryReferenceMapper;
import ibee.webapp.todo_app.core.dto.person.contact.country.PersonCountryDto;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;

@Mapper(
    config = MapStructConfig.class,
    uses = { 
            CountryMapper.class,
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
    @Mapping(target = "country", source = "country")
    @Mapping(target = "mainCountry", source = "mainCountry") 
    PersonCountry toEntity(PersonCountryDto dto);
/* 
    @Override
    default List<PersonCountryDto> toDtoList(List<PersonCountry> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    default List<PersonCountry> toEntityList(List<PersonCountryDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
            .map(this::toEntity)
            .toList();
    }

    // convenience mappings to UI id
    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "countryId", source = "id.countryId")
    PersonCountryDtoId toUiId(PersonCountry entity);

    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "countryId", source = "id.countryId")
    PersonCountryDtoId toUiId(PersonCountryDto dto);

    // collection helpersdefault List<PersonCountryUiId> toUiIdListFromEntities(List<PersonCountry> entities) {
    default List<PersonCountryDtoId> toUiIdListFromEntities(List<PersonCountry> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toUiId).toList();
    }

    default List<PersonCountryDtoId> toUiIdListFromDtos(List<PersonCountryDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toUiId).toList();
    }
*/
}
