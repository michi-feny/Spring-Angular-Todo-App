package ibee.webapp.todo_app.mapper;

import ibee.webapp.todo_app.core.dto.AddressDto;
import ibee.webapp.todo_app.core.dto.CountryDto;
import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.Country;
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
    @Mapping(target = "country", expression = "java(ibee.webapp.todo_app.core.entity.Country.referenceOf(dto.country() != null ? dto.country().id() : null))")
    Address toEntity(AddressDto dto);

    @Override
    @Mapping(target = "country", source = "country") // Explicitly bridge country -> countryDto
    AddressDto toDto(Address entity);
    
    @Override
    @Mapping(target = "country", expression = "java(ibee.webapp.todo_app.core.entity.Country.referenceOf(dto.country() != null ? dto.country().id() : null))")
    void updateEntityFromDto(AddressDto dto, @MappingTarget Address entity);

    @Override
    @Mapping(target = "country", ignore = true)
    void updateEntityFromEntity(Address sourceUpdates, @MappingTarget Address dbEntity);

    

}
