package ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonAdditionalHardSkillDtoId(
    @NotNull
    @Positive
    Long additionalHardSkillId,

    @NotNull
    @Positive
    Long personId
) {

}
