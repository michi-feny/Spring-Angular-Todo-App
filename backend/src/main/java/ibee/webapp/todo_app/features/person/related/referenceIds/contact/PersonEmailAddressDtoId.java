package ibee.webapp.todo_app.features.person.related.referenceIds.contact;

import ibee.webapp.todo_app.dto.base.StringToDtoIdConvertable;
import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.security.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PersonEmailAddressDtoId(
    @NotNull
    @Positive
    Long personId,
    
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long emailAddressId

    
) implements StringToDtoIdConvertable
{
}
