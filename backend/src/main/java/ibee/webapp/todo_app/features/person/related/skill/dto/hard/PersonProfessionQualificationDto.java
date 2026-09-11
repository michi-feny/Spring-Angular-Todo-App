package ibee.webapp.todo_app.features.person.related.skill.dto.hard;

import java.time.LocalDate;

import ibee.webapp.todo_app.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.dto.LocalDateDurationDto;
import ibee.webapp.todo_app.dto.skills.hard.ProfessionQualificationDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


public record PersonProfessionQualificationDto(

    @Valid 
    PersonProfessionQualificationDtoId id,

    @NotBlank
    String name,

    @NotNull
    @Valid
    EducationInstitutionDto educationInstitution,

    @NotNull
    @Valid
    ProfessionQualificationDto professionQualification,

    // @NotNull
    // @PastOrPresent
    //  LocalDate startDate,

    // @PastOrPresent
    //  LocalDate endDate,
    @Valid 
    LocalDateDurationDto professionQualificationDuration,

    String certificateNumber
) {

}
