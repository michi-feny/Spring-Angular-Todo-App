package ibee.webapp.todo_app.mapper.person.skill;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
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


}
