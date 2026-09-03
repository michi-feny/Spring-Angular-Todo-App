package ibee.webapp.todo_app.features.person.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.validation.constraints.Positive;
/*
 * Used by the initially open Person Overview.
*/

public record PersonData(

        @ValidId
        Long id,

        Long socialRecordNumber,

        String firstName,

        String lastName,

        LocalDate birthDate

) {
}
