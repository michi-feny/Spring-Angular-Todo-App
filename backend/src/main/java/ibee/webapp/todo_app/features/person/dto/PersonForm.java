package ibee.webapp.todo_app.features.person.dto;

import java.time.LocalDate;

/*
* Used by the Person form for create/update.
* The id is intentionally present 
* because the same form supports both:
* null ID → create
* existing ID → update
*/
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;


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
