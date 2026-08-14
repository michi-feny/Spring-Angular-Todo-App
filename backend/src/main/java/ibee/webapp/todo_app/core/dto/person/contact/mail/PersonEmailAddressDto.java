package ibee.webapp.todo_app.core.dto.person.contact.mail;

import ibee.webapp.todo_app.core.dto.EmailAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailAddressDtoId;
import jakarta.validation.constraints.NotNull;

public record PersonEmailAddressDto(

    @NotNull
    PersonEmailAddressDtoId id,

    @NotNull
    EmailAddressDto emailAddress,

    @NotNull
    Boolean mainEmail
) {

}
