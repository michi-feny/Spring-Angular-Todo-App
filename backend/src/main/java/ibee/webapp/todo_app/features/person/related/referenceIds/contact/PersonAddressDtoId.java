package ibee.webapp.todo_app.features.person.related.referenceIds.contact;


import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;


public record PersonAddressDtoId 
    (
    @NotNull
    @Positive
    Long personId,

    @NotNull(groups = OnUpdate.class)
    @Positive(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    Long addressId
) implements StringToDtoIdConvertable
{

}
