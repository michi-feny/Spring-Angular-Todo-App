package ibee.webapp.todo_app.features.person.related.referenceIds.contact;

import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonCountryDtoId(
    @NotNull
    @Positive
    Long countryId,

    @NotNull
    @Positive
    Long personId
) implements StringToDtoIdConvertable
{

}
