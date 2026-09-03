package ibee.webapp.todo_app.features.person.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
/*
* Used by the Person form for create/update.
* The id is intentionally present 
* because the same form supports both:
* null ID → create
* existing ID → update
*/
public record PersonForm(
    @ValidId
    Long id,
    String firstName,
    String lastName,
    String socialRecordNumber,
    LocalDate birthDate
) {
}
