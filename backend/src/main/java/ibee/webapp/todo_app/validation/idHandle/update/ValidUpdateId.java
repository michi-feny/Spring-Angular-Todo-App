package ibee.webapp.todo_app.validation.idHandle.update;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidUpdateIdValidator.class)
@Target({ 
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE
 })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUpdateId {
    String message() default "ID must be provided and valid for updates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
