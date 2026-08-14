package ibee.webapp.todo_app.core.dto.person.skills.soft;

import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.core.dto.skills.soft.SoftSkillDto;
import jakarta.validation.constraints.NotNull;

public record PersonSoftSkillDto(

    @NotNull
    SoftSkillDto softSkill,

    @NotNull
    PersonSoftSkillDtoId id
) {

}
