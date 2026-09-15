package ibee.webapp.todo_app.validation.dateRange;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateRangeValidator.class)
@Documented
public @interface ValidDateRange {

    // The names of the fields to compare
    String startField();
    String endField();

    String message() default "End date must be after or equal to the start date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
