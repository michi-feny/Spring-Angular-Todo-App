package ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard;

import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonProfessionQualificationDtoId(
    @NotNull
    @Positive
    Long personId,  

    @NotNull
    @Positive
    Long professionQualificationId,

    @NotNull
    @Positive
    Long educationInstitutionId
) implements StringToDtoIdConvertable
{

}
