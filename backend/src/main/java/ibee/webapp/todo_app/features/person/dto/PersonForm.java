package ibee.webapp.todo_app.features.person.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.security.validation.idHandle.update.ValidUpdateId;


public record PersonForm(
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,
    String firstName,
    String lastName,
    String socialRecordNumber,
    LocalDate birthDate
) {
}
