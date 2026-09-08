package ibee.webapp.todo_app.features.person.related.referenceIds.workExp;

import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PersonWorkExperienceDtoId(
    @NotNull
    @Positive
    Long personId,

    @NotNull
    @Positive
    Long workExperienceId
) implements StringToDtoIdConvertable{

}
