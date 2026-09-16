package ibee.webapp.todo_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChoiceValidator.class)
public @interface Choice {
    String message() default "{validation.choice}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] choices() default {};
    Class<? extends Enum<?>> enumClass() default AnyEnum.class;
    enum AnyEnum {}
}