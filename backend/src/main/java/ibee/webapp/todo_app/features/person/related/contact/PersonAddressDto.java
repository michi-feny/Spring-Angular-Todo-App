package ibee.webapp.todo_app.features.person.related.contact;

import ibee.webapp.todo_app.core.dto.AddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
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
