package ibee.webapp.todo_app.mapper.person.references.skill.soft;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonSoftSkillReferenceMapper 
    extends BaseMapper<PersonSoftSkillDtoId, PersonSoftSkillId> {

 /*    @Mapping(
        target = "softSkillId",
        source = "softSkillId"
    )
    PersonSoftSkillDtoId toModel(
            PersonSoftSkillDtoId id
    );
    */
}
