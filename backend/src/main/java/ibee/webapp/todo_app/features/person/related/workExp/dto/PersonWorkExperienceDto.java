package ibee.webapp.todo_app.features.person.related.workExp.dto;

import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;;


public record PersonWorkExperienceDto(
    @Valid 
    PersonWorkExperienceDtoId id,

    @Valid
    @NotNull
    WorkExperienceDto workExperience,
    
    @NotNull
    Integer displayOrder,

    @NotNull 
    Boolean visible,

    //todo OnMerge.class
    Long mergedIntoWorkExpId
) {

}
