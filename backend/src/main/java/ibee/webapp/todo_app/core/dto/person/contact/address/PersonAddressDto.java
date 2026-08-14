package ibee.webapp.todo_app.core.dto.person.contact.address;

import ibee.webapp.todo_app.core.dto.AddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import jakarta.validation.constraints.NotNull;

public record PersonAddressDto(

    @NotNull
    PersonAddressDtoId id,

    @NotNull
    AddressDto address,

    @NotNull
    Boolean mainAddress

) {



}
