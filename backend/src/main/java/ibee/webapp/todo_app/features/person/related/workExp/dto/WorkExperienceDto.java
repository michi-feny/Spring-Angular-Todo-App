package ibee.webapp.todo_app.features.person.related.workExp.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.dto.CompanyDto;
import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.security.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.Past;


public record WorkExperienceDto(
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
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
