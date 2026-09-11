package ibee.webapp.todo_app.security.validation.idHandle.create;

import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCreateIdValidator implements ConstraintValidator<ValidCreateId, Object> {
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        
        if (value instanceof Number) return ((Number) value).longValue() <= 0;
        if (value instanceof String) return ((String) value).trim().isEmpty();
        
        // If it's an EmbeddedKey or UUID object, let it pass here. 
        // @Valid will handle checking its internal fields if necessary.
        if (value instanceof UUID) return false; 
        
        // 4. THE TRAP PROTECTOR: Crash if placed on a DTO or Composite Key
        throw new IllegalArgumentException(
            "@ValidCreateId can only be applied to Numbers, Strings, or UUIDs. " +
            "For composite objects like PersonAddressDtoId, use @Valid instead!"
        );
    }
}
