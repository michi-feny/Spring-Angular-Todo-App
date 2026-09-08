package ibee.webapp.todo_app.core.service.baseService.newApproach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.ResolvableType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import ibee.webapp.todo_app.core.service.util.builder.CrudEntityAsserterBuilder;
import ibee.webapp.todo_app.core.service.util.builder.CrudLogAndExceptionBuilder;
import ibee.webapp.todo_app.core.service.util.builder.CustomActionBuilder;
import ibee.webapp.todo_app.core.exception.ResourceNotFoundException;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;
import ibee.webapp.todo_app.mapper.baseMaper.EntityUpdateMapper;
import ibee.webapp.todo_app.validation.EntityValidationService;

import static ibee.webapp.todo_app.core.service.util.builder.helpers.EntityReflectionUtils.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Transactional
public abstract class MyCrudBaseEntityFacedeServiceImpl<ENTITY, ID>
        implements MyCrudBaseEntityFacadeService<ENTITY, ID> {

    protected final MyFacadeBaseCrudRepository<ENTITY, ID> repository;

    protected final EntityUpdateMapper<ENTITY> entityMapper;

    @Autowired
    protected EntityValidationService jakartaValidator;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    // The two specialized semantic delegates
    protected final CrudLogAndExceptionBuilder actionEvent;
    protected final CustomActionBuilder customAction;
    protected final CrudEntityAsserterBuilder assertData;

    protected MyCrudBaseEntityFacedeServiceImpl(
            MyFacadeBaseCrudRepository<ENTITY, ID> repository,
            EntityUpdateMapper<ENTITY> entityMapper) {

        this.repository = repository;
        this.entityMapper = entityMapper;

        String entityI18nKey = generateEntityKey(
            this.getClass(), 
            MyCrudBaseEntityFacedeServiceImpl.class);
        String pureName = entityI18nKey.replace("entity.", "");
        
        // Initialize the delegates with the exact context of this service
        this.actionEvent = new CrudLogAndExceptionBuilder(this.log, entityI18nKey, pureName);
        this.assertData = new CrudEntityAsserterBuilder(pureName);
        this.customAction = new CustomActionBuilder(this.log, entityI18nKey, pureName);
    }


    protected void validateCompositeId(
        ID id, 
        String contextName
    ) {
            
        assertData.contextNotNull(contextName);
        assertData.idNotNull(id);

        // 3. Full Validation: Check the fully assembled ID
        jakartaValidator.validateState(id, contextName);
    }
    

    @Override
    public ENTITY create(ENTITY entity) {
        assertData.entityNotNull(entity);

        try {
            ENTITY saved = repository.save(entity);
            actionEvent.logInfoCreated(saved);
            
            return saved;
            
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.
                logErrorAndBuildConstraintOnCreate(e);
        }
    }

    @Override
    public ENTITY update(ENTITY sourceUpdates, ID id) {
        assertData.idNotNull(id);
        assertData.updatesNotNull(sourceUpdates);

        ENTITY originalEntity = repository.findById(id).orElseThrow(() -> 
            actionEvent.logWarnAndBuildNotFoundOnUpdate(id)
        );

        entityMapper.updateEntityFromEntity(
            sourceUpdates, 
            originalEntity
        );

        try {
            ENTITY updated = repository.save(originalEntity);
            actionEvent.logInfoUpdated(id, updated);
            
            return updated;
            
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.logErrorAndBuildConstraintOnUpdate(id, e);
        }

    }

    @Override
    public List<ENTITY> saveAll(
            Iterable<ENTITY> entities) {

        assertEntitiesAndAllTheirIdsNotNull(entities);

        try {
            List<ENTITY> saved = repository.saveAll(entities);
            actionEvent.logInfoBatchSaved(saved);
            
            return saved;
            
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.logErrorAndBuildConstraintOnBatchSave(e);
        }
    }

    @Override
    @Transactional(readOnly = true) 
    public Optional<ENTITY> findByIdWithoutException(ID id) {
        assertData.idNotNull(id);
        Optional<ENTITY> entity = repository.findById(id);
        if(entity.isPresent() == true)
            actionEvent.logInfoFoundForFindById(id);
        else
            actionEvent.logInfoNotFoundforFindById(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public ENTITY findById(ID id) {
        //now the interface signature have to be changed..
    //tell all implications for the code:
    
        ENTITY entity = findByIdWithoutException(id).orElseThrow(() ->
            actionEvent.logWarnAndBuildNotFoundOnFind(id)
        );

        
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAllById(
            Iterable<ID> ids) {

        assertIterableIdsNotNull(ids);

        var entitiesIdList =  repository.findAllById(ids);
        if(entitiesIdList!= null 
            && entitiesIdList.isEmpty() == true
        ){
            throw actionEvent.
                logWarnAndBuildNotFoundOnFindAllById(ids);
        }

        actionEvent.logInfoFoundAllById(ids);
        return entitiesIdList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAll() {
        List<ENTITY> entities = repository.findAll();
        
        if (entities == null || 
            (entities!= null && entities.isEmpty())) {

            throw actionEvent.
                logWarnAndBuildNotFoundOnFindAll();
        }
        
        actionEvent.logInfoFoundAll();

        return entities;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        assertData.idNotNull(id);

        boolean exists = repository.existsById(id);
        
        actionEvent.logInfoExists(id, exists);
        
        return exists;
    }
    
    @Override
    public void deleteById(ID id) {
        assertData.idNotNull(id);

        //Assert.notNull(id, "ID cannot be null");

        if (repository.existsById(id) == false) {
            throw actionEvent.
                logWarnAndBuildNotFoundOnDelete(id);
        }

        try {
            repository.deleteById(id);
            actionEvent.logInfoDeleted(id);
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.
                logErrorAndBuildConstraintOnDelete(id, e);
        }
    }
    


    @Override
    public void delete(ENTITY entity) {
       // Assert.notNull(entity, "Entity cannot be null");
        assertEntitiyAndItsIdNotNull(entity);
       
       try {
            repository.delete(entity);
            actionEvent.logInfoDeletedEntity(entity);
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.
                logErrorAndBuildConstraintOnDeleteEntity(e); 
        }
    }


    @Override
    public void deleteAllById(
            Iterable<? extends ID> ids) {

        assertIterableIdsNotNull(ids);

        try {
            repository.deleteAllById(ids);
            actionEvent.logInfoBatchDeleted(ids);
            
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.
                logErrorAndBuildConstraintOnDelete(ids, e);
        }
    }

    @Override
    public void deleteAll(
            Iterable<? extends ENTITY> entities) {

        assertEntitiesAndAllTheirIdsNotNull(entities);
       
        try {
            repository.deleteAll(entities);
            actionEvent.logInfoBatchDeletedEntities();
            
        } catch (DataIntegrityViolationException e) {
            throw actionEvent.
                logErrorAndBuildConstraintOnDeleteEntity(e);
        }

    }

    private void assertIterableIdsNotNull(Iterable<? extends ID> ids){
        assertData.iterableNotNull(ids);
        for(ID id: ids){
            assertData.idNotNull(id);
        }
    }

    private void assertEntitiyAndItsIdNotNull(ENTITY entity){
        assertData.entityNotNull(entity);
        ID id = extractIdDynamically(entity);
        assertData.idNotNull(id);
    }

    private void assertEntitiesAndAllTheirIdsNotNull(
        Iterable<? extends ENTITY> entities
    ){
        assertData.iterableNotNull(entities);
        for(ENTITY entity: entities){
            assertEntitiyAndItsIdNotNull(entity);
        }
    }
}
