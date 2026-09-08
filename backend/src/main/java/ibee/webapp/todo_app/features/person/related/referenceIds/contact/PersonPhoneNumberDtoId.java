package ibee.webapp.todo_app.features.person.related.referenceIds.contact;

import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PersonPhoneNumberDtoId(
        @NotNull
        @Positive       
        Long personId,

        @NotNull
        @Positive
        Long phoneNumberId
) implements StringToDtoIdConvertable
{
}
