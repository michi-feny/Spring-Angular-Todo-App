package ibee.webapp.todo_app.mapper.person.references.skill.soft;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillUiId;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;

@Mapper(config = MapStructConfig.class)
public interface PersonSoftSkillReferenceMapper {

    @Mapping(
        target = "softSkillId",
        source = "softSkillId"
    )
    PersonSoftSkillUiId toModel(
            PersonSoftSkillUiId id
    );
}
