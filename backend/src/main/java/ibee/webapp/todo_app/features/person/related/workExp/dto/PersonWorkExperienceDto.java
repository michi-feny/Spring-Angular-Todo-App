package ibee.webapp.todo_app.features.person.related.workExp.dto;

import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import jakarta.validation.Valid;


public record PersonWorkExperienceDto(
    @Valid 
    PersonWorkExperienceDtoId id,
    WorkExperienceDto workExperience,
    Integer displayOrder,
    Boolean visible,
    Long mergedIntoWorkExpId
) {

}
