package ibee.webapp.todo_app.features.person.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import jakarta.validation.constraints.NotNull;
/*
 * Used by the initially open Person Overview.
*/
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record PersonData(


        //@ValidId(groups = OnUpdate.class)
        @Null(groups = OnCreate.class)
        @NotNull(groups = OnUpdate.class)
        @Positive(groups = OnUpdate.class)
        Long id,

        Long socialRecordNumber,

        String firstName,

        String lastName,

        LocalDate birthDate

) {
}
