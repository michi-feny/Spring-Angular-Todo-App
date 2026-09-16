package ibee.webapp.todo_app.features.person.related.workExp.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.dto.CompanyDto;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;


public record WorkExperienceDto(
    
    
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,

    @Past
    LocalDate startDate,

    LocalDate endDate,

    @NotNull 
    @NotEmpty 
    String jobTitle,

    @NotNull 
    String description,


    @NotNull 
    Boolean militaryService,

    @Valid 
    @ConvertGroup(from = OnCreate.class, to = Default.class)
    @ConvertGroup(from = OnUpdate.class, to = Default.class)
    CompanyDto company
) {

}
