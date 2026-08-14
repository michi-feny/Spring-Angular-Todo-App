package ibee.webapp.todo_app.core.dto.person;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
/*
 * Used by the initially open Person Overview.
*/
public record PersonData(

        @Positive
        Long id,

        short socialRecordNumber,

        String firstName,

        String lastName,

        LocalDate birthDate

) {
}
