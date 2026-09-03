package ibee.webapp.todo_app.validation.idHandle;

import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.Valid;
import java.lang.annotation.*;

@ValidCreateId(groups = OnCreate.class)
@ValidUpdateId(groups = OnUpdate.class)
@Valid // Automatically cascades into EmbeddedKeys
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {}) // Empty because it delegates to the annotations above
@Documented
public @interface ValidId {
    
    String message() default "ID is invalid for the requested operation";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
