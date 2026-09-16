package ibee.webapp.todo_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minLength() default 10;
    boolean requireNumber() default true;
    boolean requireLowerCase() default true;
    boolean requireUpperCase() default true;
    boolean requireSpecialChar() default true;
}