package ibee.webapp.todo_app.features.person.related.contact;

import ibee.webapp.todo_app.core.dto.AddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
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
