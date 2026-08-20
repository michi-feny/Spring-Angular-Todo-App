package ibee.webapp.todo_app.core.dto.person.skills.hard;

import java.time.LocalDate;

import ibee.webapp.todo_app.core.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.core.dto.skills.hard.ProfessionQualificationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record PersonProfessionQualificationDto(

    
    PersonProfessionQualificationDtoId id,

    @NotBlank
    String name,

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
