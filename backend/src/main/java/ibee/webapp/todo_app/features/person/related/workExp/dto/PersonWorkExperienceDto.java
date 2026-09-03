package ibee.webapp.todo_app.features.person.related.workExp.dto;

import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import ibee.webapp.todo_app.validation.idHandle.ValidId;

public record PersonWorkExperienceDto(
    @ValidId
    PersonWorkExperienceDtoId id,
    WorkExperienceDto workExperience,
    Integer displayOrder,
    Boolean visible,
    Long mergedIntoWorkExpId
) {

}
