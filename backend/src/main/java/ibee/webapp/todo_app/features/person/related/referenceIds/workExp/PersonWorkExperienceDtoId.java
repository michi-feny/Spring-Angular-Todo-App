package ibee.webapp.todo_app.features.person.related.referenceIds.workExp;

import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonWorkExperienceDtoId(
    @NotNull
    @Positive
    Long workExperienceId,

    @NotNull
    @Positive
    Long personId
) implements StringToDtoIdConvertable{

}
