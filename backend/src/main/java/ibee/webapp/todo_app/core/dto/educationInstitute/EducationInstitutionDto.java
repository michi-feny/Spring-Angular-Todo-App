package ibee.webapp.todo_app.core.dto.educationInstitute;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EducationInstitutionDto(

    @NotNull
    @Positive
    Long id,

    @NotNull
    CreateEducationInstitutionDto createdEducationInstitutionDto
) {

}
