package ibee.webapp.todo_app.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.Company;
import ibee.webapp.todo_app.dto.CompanyDto;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
        config = MapStructConfig.class,
        uses = {
                AddressMapper.class
        }
)
public interface CompanyMapper
        extends BaseMapper<CompanyDto, Company> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    CompanyDto toDto(Company entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address") // Handled exclusively via service-layer address lookup/creation
    Company toEntity(CompanyDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", source = "address")
    void updateEntityFromDto(
        CompanyDto dto, 
        @MappingTarget Company entity
);

    @Override
    @BeanMapping(
        nullValuePropertyMappingStrategy = 
        NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    void updateEntityFromEntity(
        Company sourceUpdates, 
        @MappingTarget Company dbEntity
);
}
