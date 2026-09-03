package ibee.webapp.todo_app.core.service.baseService.newApproach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;
import ibee.webapp.todo_app.mapper.baseMaper.EntityUpdateMapper;
import ibee.webapp.todo_app.validation.EntityValidationService;

import java.util.List;
import java.util.Optional;



@Transactional
public abstract class MyCrudBaseEntityFacedeServiceImpl<ENTITY, ID>
        implements MyCrudBaseEntityFacadeService<ENTITY, ID> {

    protected final MyFacadeBaseCrudRepository<ENTITY, ID> repository;
    protected final EntityUpdateMapper<ENTITY> entityMapper;
    @Autowired
    protected EntityValidationService jakartaValidator;

    protected MyCrudBaseEntityFacedeServiceImpl(
            MyFacadeBaseCrudRepository<ENTITY, ID> repository,
            EntityUpdateMapper<ENTITY> entityMapper) {

        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    protected void validateCompositeId(ID id, String contextName) {
        Assert.notNull(contextName, "contextName cannot be null");
        Assert.notNull(id, contextName + " cannot be null");
        
        // 3. Full Validation: Check the fully assembled ID
        jakartaValidator.validateState(id, contextName);
    }
    

    @Override
    public ENTITY create(ENTITY entity) {
        return repository.save(entity);
    }

    @Override
    public ENTITY update(ENTITY sourceUpdates, ID id) {
        
        ENTITY originalEntity = repository.findById(id).orElseThrow();
        entityMapper.updateEntityFromEntity(sourceUpdates, originalEntity);;
        return repository.save(originalEntity);
    }

    @Override
    public List<ENTITY> saveAll(
            Iterable<ENTITY> entities) {

        return repository.saveAll(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ENTITY> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAllById(
            Iterable<ID> ids) {

        return repository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
    
    @Override
    public void deleteById(ID id) {
        Assert.notNull(id, "ID cannot be null");
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete this record because it is actively referenced by other entities.");
        }
    }

    @Override
    public void delete(ENTITY entity) {
        Assert.notNull(entity, "Entity cannot be null");
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete this record because it is actively referenced by other entities.");
        }
    }

    

    @Override
    public void deleteAllById(
            Iterable<? extends ID> ids) {

        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(
            Iterable<? extends ENTITY> entities) {

        repository.deleteAll(entities);
    }
}
