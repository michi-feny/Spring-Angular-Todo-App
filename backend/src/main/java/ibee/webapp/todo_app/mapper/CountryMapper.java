package ibee.webapp.todo_app.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

   
    // Da die Entität geschützt (immutable) ist und wir Länder nie aus einem DTO 
    // heraus neu in der DB speichern, sagen wir MapStruct: "Ignoriere diese Felder einfach".
    @Override
    @BeanMapping(ignoreByDefault = true) // <-- DER HOLZHAMMER! Blockiert alle automatischen Set-Versuche.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "translations", ignore = true) 
    Country toEntity(CountryDto dto);
}
