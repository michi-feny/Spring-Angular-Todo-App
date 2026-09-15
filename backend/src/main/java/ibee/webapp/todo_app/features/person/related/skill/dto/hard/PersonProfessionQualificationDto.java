package ibee.webapp.todo_app.features.person.related.skill.dto.hard;


import ibee.webapp.todo_app.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.dto.LocalDateDurationDto;
import ibee.webapp.todo_app.dto.skills.hard.ProfessionQualificationDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;


public record PersonProfessionQualificationDto(

    @Valid 
    PersonProfessionQualificationDtoId id,

    @NotBlank
    String name,

    @NotNull
    @Valid
    @ConvertGroup(from = OnCreate.class, to = Default.class)
    @ConvertGroup(from = OnUpdate.class, to = Default.class)
    EducationInstitutionDto educationInstitution,

    @NotNull
    @Valid
    @ConvertGroup(from = OnCreate.class, to = Default.class)
    @ConvertGroup(from = OnUpdate.class, to = Default.class)
    ProfessionQualificationDto professionQualification,

    // @NotNull
    // @PastOrPresent
    //  LocalDate startDate,

    // @PastOrPresent
    //  LocalDate endDate,
    @Valid 
    @NotNull 
    LocalDateDurationDto professionQualificationDuration,

    String certificateNumber
) {

}
