package ibee.webapp.todo_app.validation.idHandle;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.lang.annotation.*;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;

@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {}) 
@NotNull(groups = OnUpdate.class, message = "ID must not be null on update")
@Positive(groups = OnUpdate.class, message = "ID must be a positive number")
@Documented
public @interface ValidId {
    String message() default "Invalid ID for the requested operation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}