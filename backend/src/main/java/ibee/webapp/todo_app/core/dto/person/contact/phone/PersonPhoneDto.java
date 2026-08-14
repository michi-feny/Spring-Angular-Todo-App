package ibee.webapp.todo_app.core.dto.person.contact.phone;

import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneUiId;
import ibee.webapp.todo_app.core.dto.phoneNumber.CreatePhoneNumberDto;
import jakarta.validation.constraints.NotNull;

public record PersonPhoneDto(
    @NotNull
    PersonPhoneUiId id,

    @NotNull
    CreatePhoneNumberDto phoneNumber,

    @NotNull
    Boolean mainNumber
) {

}
