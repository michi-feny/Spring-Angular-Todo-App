package ibee.webapp.todo_app.validation.idHandle.create;

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
        return true; 
    }
}
