package ibee.webapp.todo_app.security.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;

@Component
public class EntityValidationService {

    private final Validator validator;

    @Autowired
    public EntityValidationService(Validator validator) {
        this.validator = validator;
    }

    /**
     * Globally validates any entire object against its Jakarta annotations.
     */
    public void validateState(Object target, String contextName) {
        Assert.notNull(target, contextName + " cannot be null");
        Set<ConstraintViolation<Object>> violations = validator.validate(target);
        
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(contextName + " contains invalid data", violations);
        }
    }

    /**
     * Globally validates a single specific property of any object.
     */
    public void validateProperty(Object target, String propertyName, String contextName) {
        Assert.notNull(target, contextName + " cannot be null");
        Set<ConstraintViolation<Object>> violations = validator.validateProperty(target, propertyName);
        
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(contextName + " contains invalid data", violations);
        }
    }
}
