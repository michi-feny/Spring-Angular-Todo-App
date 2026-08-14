package ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonDegreeDtoId(
    @NotNull
    @Positive
    Long degreeId,

    @NotNull
    @Positive
    Long personId,

    @NotNull
    @Positive
    Long educationInstitutionId
) {

}
