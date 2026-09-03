package ibee.webapp.todo_app.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.CountryDto;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
        config = MapStructConfig.class, 
        uses = { CountryTranslationMapper.class })
public interface CountryMapper 
        extends BaseMapper<CountryDto, Country> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = ".", qualifiedByName = "extractTranslatedName")
    @Mapping(target = "language", source = ".", qualifiedByName = "extractLanguageCode")
    CountryDto toDto(Country entity);

   
    /**
     * Intercepts and overrides the default mapping contract to prevent MapStruct from 
     * attempting to reflectively invoke a protected no-arg constructor (`new Country()`). 
     * <p>
     * <b>Domain Protection Rule:</b> Immutable reference data should never be created on-the-fly 
     * from inbound client DTO payloads. Entity association and lifecycle management (such as fetching 
     * pre-seeded records by foreign keys or codes) are strictly delegated to the Service Layer.
     *
     * @param dto The incoming country data transfer object.
     * @return Always returns {@code null}, as direct instantiation from DTOs is blocked.
     */
    @Override
    default Country toEntity(CountryDto dto) {
        if (dto == null || dto.id() == null) {
            return null;
        }
        return Country.referenceOf(dto.id());
    }

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "translations", ignore = true)
    void updateEntityFromDto(CountryDto dto, @MappingTarget Country entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "translations", ignore = true)
    void updateEntityFromEntity(Country sourceUpdates, @MappingTarget Country dbEntity);
}
