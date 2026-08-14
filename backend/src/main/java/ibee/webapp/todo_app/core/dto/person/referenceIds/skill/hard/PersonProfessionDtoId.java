package ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonProfessionDtoId(
    @NotNull
    @Positive
    Long professionQualificationId,

    @NotNull
    @Positive
    Long personId,  

    @NotNull
    @Positive
    Long educationInstitutionId
) {

}
