package ibee.webapp.todo_app.features.person.related.workExp.dto;

import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


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
