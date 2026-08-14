package ibee.webapp.todo_app.core.dto.person.contact.mail;

import ibee.webapp.todo_app.core.dto.EmailAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailUiId;
import jakarta.validation.constraints.NotNull;

public record PersonEmailDto(

    @NotNull
    PersonEmailUiId id,

    @NotNull
    EmailAddressDto emailAddress,

    @NotNull
    Boolean mainEmail
) {

}
