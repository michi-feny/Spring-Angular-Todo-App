package ibee.webapp.todo_app.core.dto.person;

import java.time.LocalDate;
/*
* Used by the Person form for create/update.
* The id is intentionally present 
* because the same form supports both:
* null ID → create
* existing ID → update
*/
public record PersonForm(
    Long id,
    String firstName,
    String lastName,
    String socialRecordNumber,
    LocalDate birthDate
) {
}
