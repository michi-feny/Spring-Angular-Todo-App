package ibee.webapp.todo_app.mapper.person.references.workExp;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
    config = MapStructConfig.class)
public interface PersonWorkExperienceReferenceMapper 
    extends BaseMapper<PersonWorkExperienceDtoId, PersonWorkExperienceId>{

}
