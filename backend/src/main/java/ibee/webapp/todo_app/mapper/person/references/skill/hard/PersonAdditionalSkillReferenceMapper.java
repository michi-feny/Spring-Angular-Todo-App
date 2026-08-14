package ibee.webapp.todo_app.mapper.person.references.skill.hard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonAdditionalUiId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;

@Mapper(config = MapStructConfig.class)
public interface PersonAdditionalSkillReferenceMapper {

    @Mapping(
        target = "additionalHardSkillId",
        source = "additionalHardSkillId"
    )
    PersonAdditionalUiId toModel(
            PersonAdditionalHardSkillId id
    );
}
