package ibee.webapp.todo_app.core.dto.person.skills.hard;

import java.time.LocalDate;

import ibee.webapp.todo_app.core.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionDtoId;
import ibee.webapp.todo_app.core.dto.skills.hard.ProfessionQualificationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record PersonProfessionQualificationDto(

    @Deprecated
    PersonProfessionDtoId id,

    @NotBlank
    String name,

    @NotNull
    String description,

    @NotNull
    EducationInstitutionDto educationInstitution,

    @NotNull
    ProfessionQualificationDto professionQualification,

    @NotNull
    @PastOrPresent
     LocalDate startDate,

    @PastOrPresent
     LocalDate endDate,

    String certificateNumber
) {

}
