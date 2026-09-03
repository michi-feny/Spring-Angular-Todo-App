package ibee.webapp.todo_app.core.dto.person.contact.phone;

import ibee.webapp.todo_app.core.dto.PhoneNumberDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import jakarta.validation.constraints.NotNull;

public record PersonPhoneNumberDto(
    @NotNull
    PersonPhoneNumberDtoId id,

    @NotNull
    PhoneNumberDto phoneNumber,

    @NotNull
    Boolean mainPhoneNumber
) {

}
