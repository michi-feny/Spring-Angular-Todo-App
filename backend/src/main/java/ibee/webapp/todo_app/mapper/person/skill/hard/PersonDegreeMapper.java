package ibee.webapp.todo_app.mapper.person.skill.hard;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonDegreeDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
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
}
