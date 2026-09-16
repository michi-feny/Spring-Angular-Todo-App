package ibee.webapp.todo_app.features.person.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotNull;
/*
 * Used by the initially open Person Overview.
*/
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record PersonData(


        //@ValidId(groups = OnUpdate.class)
        @ValidCreateId(groups = OnCreate.class)
        @ValidUpdateId(groups = OnUpdate.class)
        Long id,

        Long socialRecordNumber,

        String firstName,

        String lastName,

        LocalDate birthDate

) {
}
