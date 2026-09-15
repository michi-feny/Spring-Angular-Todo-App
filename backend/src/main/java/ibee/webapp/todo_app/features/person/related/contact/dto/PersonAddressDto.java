package ibee.webapp.todo_app.features.person.related.contact.dto;

import ibee.webapp.todo_app.dto.AddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;

public record PersonAddressDto(

    //@ValidCreateId(groups = OnCreate.class)
    //@ValidUpdateId(groups = OnUpdate.class)
    @NotNull
    @Valid
    @ConvertGroup(from = OnCreate.class, to = OnCreate.class)
    @ConvertGroup(from = OnUpdate.class, to = OnUpdate.class)
    PersonAddressDtoId id,

    @NotNull
    @Valid
    @ConvertGroup(from = OnCreate.class, to = OnCreate.class)
    @ConvertGroup(from = OnUpdate.class, to = OnUpdate.class)
    AddressDto address,

    @NotNull
    Boolean mainAddress

) {



}
