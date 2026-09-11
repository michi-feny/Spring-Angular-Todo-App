package ibee.webapp.todo_app.features.person.related.skill.dto.soft;

import ibee.webapp.todo_app.dto.skills.hard.soft.SoftSkillDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PersonSoftSkillDto(

    @NotNull
    @Valid
    SoftSkillDto softSkill,

    @Valid
    PersonSoftSkillDtoId id
) {

}
