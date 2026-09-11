package ibee.webapp.todo_app.features.person.related.contact.dto;

import ibee.webapp.todo_app.dto.PhoneNumberDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PersonPhoneNumberDto(
    @Valid 
    PersonPhoneNumberDtoId id,

    @NotNull
    PhoneNumberDto phoneNumber,

    @NotNull
    Boolean mainPhoneNumber
) {

}
