package ibee.webapp.todo_app.core.dto.person.contact.address;

import ibee.webapp.todo_app.core.dto.address.CreateAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressUiId;
import jakarta.validation.constraints.NotNull;

public record PersonAddressDto(

    @NotNull
    PersonAddressUiId id,

    @NotNull
    CreateAddressDto address,

    @NotNull
    Boolean mainAddress

) {



}
