package ibee.webapp.todo_app.features.person.related.workExp.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.core.dto.CompanyDto;
import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.validation.constraints.Past;


public record WorkExperienceDto(
    @ValidId
    Long id,
    @Past
    LocalDate startDate,
    LocalDate endDate,
    String jobTitle,
    String description,
    boolean militaryService,
    CompanyDto company
) {

}
