package ibee.webapp.todo_app.core.service.util.builder;

import static ibee.webapp.todo_app.core.service.util.builder.helpers.CrudLogMessages.*;

import org.slf4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import ibee.webapp.todo_app.core.exception.ResourceNotFoundException;

public class CrudLogAndExceptionBuilder {

    private final Logger log;
    private final String entityKey;
    private final String pureName;

    public CrudLogAndExceptionBuilder(Logger log, String entityKey, String pureName) {
        this.log = log;
        this.entityKey = entityKey;
        this.pureName = pureName;
    }

    
    // --- EXCEPTIONS: NOT FOUND (Logs WARN) ---

    public ResourceNotFoundException logWarnAndBuildNotFoundOnFind(Object id) {
        logInfoNotFoundforFindById(id);
        //log.warn(LOG_NOT_FOUND_FIND, pureName, id);
        return buildNotFound(id, I18N_KEY_NOT_FOUND_SINGLE);
    }

    public ResourceNotFoundException logWarnAndBuildNotFoundOnUpdate(Object id) {
        log.warn(LOG_NOT_FOUND_UPDATE, pureName, id);
        return buildNotFound(id, I18N_KEY_NOT_FOUND_SINGLE);
    }

    public ResourceNotFoundException logWarnAndBuildNotFoundOnDelete(Object id) {
        log.warn(LOG_NOT_FOUND_DELETE, pureName, id);
        return buildNotFound(id, I18N_KEY_NOT_FOUND_SINGLE);
    }

    public ResourceNotFoundException logWarnAndBuildNotFoundOnFindAllById(Iterable<?> ids) {
        log.warn(LOG_NOT_FOUND_FIND, pureName, ids);
        return buildNotFound(ids, I18N_KEY_NOT_FOUND_MULTIPLE);
    }

    public ResourceNotFoundException logWarnAndBuildNotFoundOnFindAll() {
        log.warn(LOG_NOT_FOUND_FIND_ALL, pureName);
        return new ResourceNotFoundException(
            EX_MSG_RESOURCE_NOT_FOUND, 
            I18N_KEY_NOT_FOUND_ALL, 
            new DefaultMessageSourceResolvable(entityKey), 
            null
        );
    }
    // --- EXCEPTIONS: CONSTRAINT VIOLATIONS ON SAVE/UPDATE ---

    public IllegalStateException logErrorAndBuildConstraintOnCreate(Exception e) {
        log.error("Failed to create {} due to constraint violation", pureName, e);
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    public IllegalStateException logErrorAndBuildConstraintOnUpdate(Object id, Exception e) {
        log.error("Failed to update {} with ID {} due to constraint violation", pureName, id, e);
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    public IllegalStateException logErrorAndBuildConstraintOnBatchSave(Exception e) {
        log.error("Failed to batch save {} records due to constraint violation", pureName, e);
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    private ResourceNotFoundException buildNotFound(Object idOrIds, String i18nKey) {
        return new ResourceNotFoundException(
            EX_MSG_RESOURCE_NOT_FOUND, 
            i18nKey, 
            new DefaultMessageSourceResolvable(entityKey), 
            idOrIds
        );
    }

    // --- EXCEPTIONS: CONSTRAINT VIOLATIONS (Logs ERROR) ---

    public IllegalStateException logErrorAndBuildConstraintOnDelete(Object id, Exception e) {
        log.error(LOG_DELETE_FAILED, pureName, id, e);
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    public IllegalStateException logErrorAndBuildConstraintOnDeleteEntity(Exception e) {
        log.error(LOG_DELETE_ENTITY_FAILED, pureName, e);
        return new IllegalStateException(ERR_MSG_CONSTRAINT_VIOLATION);
    }

    // --- SUCCESS LOGGING (Logs INFO) ---

    public void logInfoCreated(Object savedEntity) {
        log.info(LOG_CREATE_SUCCESS + " Data: {}", pureName, savedEntity);
    }

    public void logInfoUpdated(Object id, Object updatedEntity) {
        log.info(LOG_UPDATE_SUCCESS + " Data: {}", pureName, id, updatedEntity);
    }

    public void logInfoDeleted(Object id) {
        log.info(LOG_DELETE_SUCCESS, pureName, id);
    }

    public void logInfoDeletedEntity(Object deletedEntity) {
        log.info(LOG_DELETE_ENTITY_SUCCESS + " Data: {}", pureName, deletedEntity);
    }

    public void logInfoBatchSaved(Iterable<?> entities) {
        log.info(LOG_BATCH_SAVE_SUCCESS + " Data: {}", pureName, entities);
    }

    public void logInfoBatchDeleted(Iterable<?> ids) {
        log.info(LOG_BATCH_DELETE_SUCCESS + " IDs: {}", pureName, ids);
    }

    public void logInfoBatchDeletedEntities() {
        log.info(LOG_BATCH_DELETE_ENTITIES_SUCCESS, pureName);
    }

    // --- SUCCESS LOGGING: READ OPERATIONS ---

    public void logInfoFoundForFindById(Object id) {
        
        log.info(LOG_FOUND_SUCCESS, pureName, id);
    }

    public void logInfoNotFoundforFindById(Object id) {
        log.warn(LOG_NOT_FOUND_FIND, pureName, id);
    }
    

    public void logInfoFoundAllById(Iterable<?> ids) {
       // "Successfully found {} records for IDs: {}"
        log.info(LOG_FOUND_ALL_SUCCESS, pureName, ids);
    }

    public void logInfoFoundAll() {
        //"Successfully fetched all {} records"
        log.info(LOG_FOUND_ALL_SUCCESS, pureName);
    }

    public void logInfoExists(Object id, boolean exists) {
        //"Checked existence for {} with ID: {} -> {}"
        log.info(LOG_EXISTS_CHECK, pureName, id, exists);
    }
}
