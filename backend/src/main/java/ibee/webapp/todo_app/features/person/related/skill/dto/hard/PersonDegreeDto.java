package ibee.webapp.todo_app.features.person.related.skill.dto.hard;

import java.time.LocalDate;

import ibee.webapp.todo_app.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.dto.LocalDateDurationDto;
import ibee.webapp.todo_app.dto.skills.hard.DegreeDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonDegreeDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


public record PersonDegreeDto(
    @Valid 
    PersonDegreeDtoId id,

    @NotNull
    @Valid 
    DegreeDto degree,

    @NotNull
    @Valid 
    EducationInstitutionDto educationInstitution,

    @Valid 
    @NotNull 
    LocalDateDurationDto degreeDuration,


    /*
        how much persent are finisched of this degree
    */
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    double progressInPercent


) {

}
