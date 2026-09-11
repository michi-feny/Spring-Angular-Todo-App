package ibee.webapp.todo_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.core.entity.PhoneNumber;
import ibee.webapp.todo_app.dto.PhoneNumberDto;

@Mapper(config = MapStructConfig.class,
    uses ={
        CountryMapper.class
    }
)
public interface PhoneNumberMapper 
    extends BaseMapper<PhoneNumberDto, PhoneNumber> {

    @Override
    @Mapping(
        target = "nationalityId", 
        // Extracts the ID from the Entity's Country relationship to populate the DTO
        expression = """
            java(entity.getCountry() != null ? 
            entity.getCountry().getId() 
            : null)
            """
    )
    PhoneNumberDto toDto(PhoneNumber entity);

    @Override
    @Mapping(
        target = "country", 
        // Maps the DTO's nationalityId to the Entity's Country reference
        expression = """
            java(dto.nationalityId() != null ? 
            ibee.webapp.todo_app.core.entity.Country
                .referenceOf(dto.nationalityId()) 
            : null)
            """
    )
    @Mapping(target = "fullNumber", ignore = true)
    PhoneNumber toEntity(PhoneNumberDto dto);

    @Override
    @Mapping(
        target = "country", 
        // Handles updates from DTO -> Entity the exact same way as toEntity
        expression = """
            java(dto.nationalityId() != null ? 
            ibee.webapp.todo_app.core.entity.Country
                .referenceOf(dto.nationalityId()) 
            : null)
            """
    )
    void updateEntityFromDto(PhoneNumberDto dto, @MappingTarget PhoneNumber entity);

    @Override
    @Mapping(target = "country", ignore = true)
    void updateEntityFromEntity(PhoneNumber sourceUpdates, @MappingTarget PhoneNumber dbEntity);
}
