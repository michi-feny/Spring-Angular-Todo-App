package ibee.webapp.todo_app.core.dto.person.contact.phone;

import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.core.dto.PhoneNumberDto;
import jakarta.validation.constraints.NotNull;

public record PersonPhoneNumberDto(
    @NotNull
    PersonPhoneNumberDtoId id,

    @NotNull
    PhoneNumberDto phoneNumber,

    @NotNull
    Boolean mainNumber
) {

}
