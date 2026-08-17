package ibee.webapp.todo_app.mapper.person.references.skill.hard;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonDegreeReferenceMapper 
    extends BaseMapper<PersonDegreeDtoId, PersonDegreeId> {

    //PersonDegreeDtoId toModel(PersonDegreeDtoId id);
}
