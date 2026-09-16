package ibee.webapp.todo_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualToValidator.class)
@Documented
public @interface EqualTo {
    String message() default "{validation.equalTo}";
    String matchName() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field();
    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EqualTo[] value();
    }
}