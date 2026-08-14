package ibee.webapp.todo_app.mapper.person.references.skill.hard;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeUiId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;

@Mapper(config = MapStructConfig.class)
public interface PersonDegreeReferenceMapper {

    PersonDegreeUiId toModel(PersonDegreeUiId id);
}
