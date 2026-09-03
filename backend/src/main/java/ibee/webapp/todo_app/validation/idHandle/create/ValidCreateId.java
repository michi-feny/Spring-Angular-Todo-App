package ibee.webapp.todo_app.validation.idHandle.create;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidCreateIdValidator.class)
@Target({ 
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCreateId {
    String message() default "ID must be null, empty, or <= 0 for creation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
