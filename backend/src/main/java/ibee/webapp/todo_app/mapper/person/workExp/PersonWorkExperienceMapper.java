package ibee.webapp.todo_app.mapper.person.workExp;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.features.person.related.workExp.dto.PersonWorkExperienceDto;
import ibee.webapp.todo_app.mapper.WorkExperienceMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.workExp.PersonWorkExperienceReferenceMapper;
@Mapper(
    config = MapStructConfig.class,
    uses = {
        WorkExperienceMapper.class,
        PersonWorkExperienceReferenceMapper.class
    }
)
public interface PersonWorkExperienceMapper 
    extends BaseMapper<PersonWorkExperienceDto, PersonWorkExperience> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "workExperience", source = "workExperience")
    @Mapping(target = "displayOrder", source = "displayOrder")
    @Mapping(
        target = "visible", 
        source = "visible", 
        defaultValue = "true")
    @Mapping(
        target = "mergedIntoWorkExpId", 
        source = "mergedIntoWorkExpId")
    PersonWorkExperienceDto toDto(PersonWorkExperience entity);

    @Override
    @Mapping(target = "id", source = "id")
    //@Mapping(target = "person", ignore = true)
    @Mapping(target = "person.id", source = "id.personId")
   @Mapping(target = "workExperience", source = "workExperience") // Handled via service-layer lookup/creation
    @Mapping(target = "displayOrder", source = "displayOrder")
    @Mapping(
        target = "visible", 
        source = "visible", defaultValue = "true")
    @Mapping(target = "mergedInto", ignore = true)
   // @Mapping(target = "mergedIntoWorkExpId", source = "mergedIntoWorkExpId")
   @Mapping(target = "subExperiences", ignore = true) 
   PersonWorkExperience toEntity(PersonWorkExperienceDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "workExperience", source = "workExperience")
    @Mapping(target = "mergedInto", ignore = true)
    @Mapping(target = "subExperiences", ignore = true)
    @Mapping(
        target = "visible", 
        source = "visible", defaultValue = "true")
    void updateEntityFromDto(
        PersonWorkExperienceDto dto, 
        @MappingTarget PersonWorkExperience entity
    );

    @Override
    @BeanMapping(
        nullValuePropertyMappingStrategy = 
        NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "workExperience", ignore = true)
    @Mapping(target = "mergedInto", ignore = true)
    @Mapping(target = "subExperiences", ignore = true)
    void updateEntityFromEntity(PersonWorkExperience sourceUpdates, @MappingTarget PersonWorkExperience dbEntity);
}
