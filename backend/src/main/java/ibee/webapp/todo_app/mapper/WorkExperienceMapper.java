package ibee.webapp.todo_app.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.WorkExperience;
import ibee.webapp.todo_app.features.person.related.workExp.dto.WorkExperienceDto;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {CompanyMapper.class}
 )
public interface WorkExperienceMapper extends BaseMapper<WorkExperienceDto, WorkExperience> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "jobTitle", source = "jobTitle")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "militaryService", source = "militaryService", defaultValue = "false")
    @Mapping(target = "company", source = "company")
    WorkExperienceDto toDto(WorkExperience entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "jobTitle", source = "jobTitle")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "militaryService", source = "militaryService", defaultValue = "false")
    @Mapping(target = "company", ignore = true) // Resolved and managed exclusively via CompanyServiceImpl 
    WorkExperience toEntity(WorkExperienceDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntityFromDto(WorkExperienceDto dto, @MappingTarget WorkExperience entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntityFromEntity(WorkExperience sourceUpdates, @MappingTarget WorkExperience dbEntity);
}
