package ibee.webapp.todo_app.validation.idHandle.update;

import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUpdateIdValidator implements ConstraintValidator<ValidUpdateId, Object> {
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return false;
        
        if (value instanceof Number) return ((Number) value).longValue() > 0;
        if (value instanceof String) return !((String) value).trim().isEmpty();
        
        if (value instanceof UUID) return true; 
        
        // 4. THE TRAP PROTECTOR: Crash if placed on a DTO or Composite Key
        throw new IllegalArgumentException(
            "@ValidUpdateId can only be applied to Numbers, Strings, or UUIDs. " +
            "For composite objects like PersonAddressDtoId, use @Valid instead!"
        );
    }
}
