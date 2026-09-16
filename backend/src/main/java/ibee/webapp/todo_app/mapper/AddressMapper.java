package ibee.webapp.todo_app.mapper;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.dto.AddressDto;
import ibee.webapp.todo_app.dto.CountryDto;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.config.MapStructConfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;



@Mapper(
        config = MapStructConfig.class,
        uses = {
                CountryMapper.class
        }
)
public interface AddressMapper
        extends BaseMapper<AddressDto, Address> {

    @Override
    @Mapping(
        target = "country", 
        // CHANGED: Switched from dto.country().id() to dto.nationalityId() 
        // to match the new DTO structure where only the ID is present.
        expression = """
            java(dto.nationalityId() != null ? 
            ibee.webapp.todo_app.core.entity.Country
                .referenceOf(dto.nationalityId()) 
            : null)
            """
    )
    Address toEntity(AddressDto dto);

    @Override
    @Mapping(
        target = "nationalityId", 
        // CHANGED: Extracted ID directly from entity's country relationship 
        // and removed the old country -> countryDto mapping that caused constructor parameter mismatches.
        expression = """
            java(entity.getCountry() != null ? 
            entity.getCountry().getId() 
            : null)
            """
    )
    AddressDto toDto(Address entity);
    
    @Override
    @Mapping(
        target = "country", 
        // CHANGED: Updated to check and use dto.nationalityId() instead of dto.country().id().
        expression = """
            java(dto.nationalityId() != null ? 
            ibee.webapp.todo_app.core.entity.Country
                .referenceOf(dto.nationalityId()) 
            : null)
            """
    )
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(AddressDto dto, @MappingTarget Address entity);

    @Override
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromEntity(Address sourceUpdates, @MappingTarget Address dbEntity);
}