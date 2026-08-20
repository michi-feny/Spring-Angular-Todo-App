package ibee.webapp.todo_app.mapper.person.references.skill.hard;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
    config = MapStructConfig.class
    )
public interface PersonProfessionQualificationReferenceMapper 
    extends BaseMapper<PersonProfessionQualificationDtoId, PersonProfessionQualificationId> {

  
    
}
