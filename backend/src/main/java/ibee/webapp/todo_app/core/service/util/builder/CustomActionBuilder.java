package ibee.webapp.todo_app.core.service.util.builder;

import static ibee.webapp.todo_app.core.service.util.builder.helpers.CrudLogMessages.*;

import org.slf4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import ibee.webapp.todo_app.core.exception.ResourceNotFoundException;

public class CustomActionBuilder {

    private final Logger log;
    private final String entityKey;
    private final String pureName;

    public CustomActionBuilder(Logger log, String entityKey, String pureName) {
        this.log = log;
        this.entityKey = entityKey;
        this.pureName = pureName;
    }

    // --- SUCCESS LOGGING ---

    public void logInfo(String actionName, Object id) {
        log.info("ACTION_SUCCESS: '{}' completed for {} with ID: {}", actionName, pureName, id);
    }

    // --- EXCEPTIONS: NOT FOUND ---

    public ResourceNotFoundException notFound(String actionName, Object id) {
        return notFound(actionName, id, I18N_KEY_NOT_FOUND_SINGLE);
    }

    public ResourceNotFoundException notFound(String actionName, Object id, String customI18nKey) {
        log.warn("ACTION_FAILED: '{}' failed - {} with ID {} was not found.", actionName, pureName, id);
        
        return new ResourceNotFoundException(
            EX_MSG_RESOURCE_NOT_FOUND, 
            customI18nKey, 
            new DefaultMessageSourceResolvable(entityKey), 
            id
        );
    }

    // --- EXCEPTIONS: CONSTRAINT & BUSINESS RULES ---

    public IllegalStateException constraintViolation(String actionName, Object id, Exception e) {
        log.error("ACTION_FAILED: '{}' for {} with ID {} failed due to constraint violation", 
                actionName, pureName, id, e);
        
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    public IllegalStateException businessRuleViolation(String actionName, Object id, String reason) {
        log.warn("ACTION_DENIED: '{}' denied for {} with ID {}. Reason: {}", 
                actionName, pureName, id, reason);
                
        return new IllegalStateException(reason); 
    }

    // --- CUSTOM SUBCLASS OPERATIONS ---

    public void logInfoCustomAction(String actionName, Object id) {
        log.info("ACTION_SUCCESS: '{}' completed for {} with ID: {}", actionName, pureName, id);
    }

    public ResourceNotFoundException logWarnAndBuildNotFoundCustomAction(String actionName, Object id) {
        log.warn("ACTION_FAILED: '{}' failed - {} with ID {} was not found.", actionName, pureName, id);
        return buildNotFound(id, I18N_KEY_NOT_FOUND_SINGLE);
    }
    public ResourceNotFoundException logWarnAndBuildNotFoundCustomAction(
            String actionName, Object id, String customI18nKey) {
                
        log.warn("ACTION_FAILED: '{}' failed - {} with ID {} was not found.", actionName, pureName, id);
        return buildNotFound(id, customI18nKey);
    }
    private ResourceNotFoundException buildNotFound(Object idOrIds, String i18nKey) {
        return new ResourceNotFoundException(
            EX_MSG_RESOURCE_NOT_FOUND, 
            i18nKey, 
            new DefaultMessageSourceResolvable(entityKey), 
            idOrIds
        );
    }
    
    public IllegalStateException logErrorAndBuildConstraintOnCustomAction(
            String actionName, Object id, Exception e) {
                
        log.error("ACTION_FAILED: '{}' for {} with ID {} failed due to constraint violation", 
                actionName, pureName, id, e);
        
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    public IllegalStateException logWarnAndBuildBusinessRuleViolation(
            String actionName, Object id, String reason) {
                
        log.warn("ACTION_DENIED: '{}' denied for {} with ID {}. Reason: {}", 
                actionName, pureName, id, reason);
                
        return new IllegalStateException(reason); 
    }

}
