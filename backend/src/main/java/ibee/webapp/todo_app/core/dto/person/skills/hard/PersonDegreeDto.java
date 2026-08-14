package ibee.webapp.todo_app.core.dto.person.skills.hard;

import java.time.LocalDate;

import ibee.webapp.todo_app.core.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.core.dto.skills.hard.DegreeDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record PersonDegreeDto(

    PersonDegreeDtoId id,

    @NotNull
    DegreeDto degree,

    @NotNull
    EducationInstitutionDto educationInstitution,

    /*
        start date of this degree
    */
    @NotNull
    @PastOrPresent
    LocalDate startDate,

    /*
        end Date of this degree
    */
    @PastOrPresent
    LocalDate endDate,

    /*
        how much persent are finisched of this degree
    */
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    double progressInPercent


) {

}
