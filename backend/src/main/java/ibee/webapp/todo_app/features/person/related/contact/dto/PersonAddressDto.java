package ibee.webapp.todo_app.features.person.related.contact.dto;

import ibee.webapp.todo_app.dto.AddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PersonAddressDto(

    //@ValidCreateId(groups = OnCreate.class)
    //@ValidUpdateId(groups = OnUpdate.class)
    @Valid
    PersonAddressDtoId id,

    @NotNull
    @Valid
    AddressDto address,

    @NotNull
    Boolean mainAddress

) {



}
