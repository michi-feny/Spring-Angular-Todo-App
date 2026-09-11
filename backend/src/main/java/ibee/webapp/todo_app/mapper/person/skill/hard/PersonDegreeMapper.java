package ibee.webapp.todo_app.mapper.person.skill.hard;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonDegreeDto;
import ibee.webapp.todo_app.mapper.skills.hard.DegreeMapper;
import ibee.webapp.todo_app.mapper.EducationInstitutionMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonDegreeReferenceMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        DegreeMapper.class,
        EducationInstitutionMapper.class,
        PersonDegreeReferenceMapper.class
    }
)
public interface PersonDegreeMapper 
    extends 
    BaseMapper<PersonDegreeDto, PersonDegree>{



    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonDegreeDto toDto(PersonDegree entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonDegree toEntity(PersonDegreeDto dto);

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    void updateEntityFromDto(PersonDegreeDto dto, @MappingTarget PersonDegree entity);

    // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    void updateEntityFromEntity(PersonDegree sourceUpdates, @MappingTarget PersonDegree dbEntity);

}
