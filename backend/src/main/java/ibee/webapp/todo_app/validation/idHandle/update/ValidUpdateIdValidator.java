package ibee.webapp.todo_app.validation.idHandle.update;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUpdateIdValidator implements ConstraintValidator<ValidUpdateId, Object> {
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return false;
        
        if (value instanceof Number) return ((Number) value).longValue() > 0;
        if (value instanceof String) return !((String) value).trim().isEmpty();
        
        // For EmbeddedKey or UUID objects, not being null is enough here.
        // @Valid will handle the nested fields.
        return true; 
    }
}
