package ibee.webapp.todo_app.core.dto.person.skills.soft;

import ibee.webapp.todo_app.core.dto.skills.soft.SoftSkillDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.validation.constraints.NotNull;

public record PersonSoftSkillDto(

    @NotNull
    SoftSkillDto softSkill,

    @ValidId
    PersonSoftSkillDtoId id
) {

}
