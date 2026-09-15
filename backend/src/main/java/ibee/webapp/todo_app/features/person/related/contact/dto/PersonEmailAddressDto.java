package ibee.webapp.todo_app.features.person.related.contact.dto;

import ibee.webapp.todo_app.dto.EmailAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonEmailAddressDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PersonEmailAddressDto(

    @NotNull
    @Valid 
    PersonEmailAddressDtoId id,

    @NotNull
    @Valid 
    EmailAddressDto emailAddress,

    @NotNull
    Boolean mainEmail
) {

}
