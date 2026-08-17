package ibee.webapp.todo_app.mapper.person.skill.hard;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonDegreeDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.mapper.skills.hard.DegreeMapper;
import ibee.webapp.todo_app.mapper.EducationInstitutionMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        DegreeMapper.class,
        EducationInstitutionMapper.class
    }
)
public interface PersonDegreeMapper 
    extends 
    BaseMapper<PersonDegreeDto, PersonDegree>{



    @Override
    @Mapping(target = "id.personId", source = "id.personId")
    @Mapping(target = "id.degreeId", source = "degree.id")
    @Mapping(target = "id.educationInstitutionId", source = "educationInstitution.id")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonDegreeDto toDto(PersonDegree entity);

    @Override
    @Mapping(target = "id.personId", source = "id.personId")
    @Mapping(target = "id.degreeId", source = "degree.id")
    @Mapping(target = "id.educationInstitutionId", source = "educationInstitution.id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "degree.id", source = "id.degreeId")
    @Mapping(target = "educationInstitution.id", source = "id.educationInstitutionId")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonDegree toEntity(PersonDegreeDto dto);
}
