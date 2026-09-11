package ibee.webapp.todo_app.core.service.person.related.contact.phone;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ibee.webapp.todo_app.core.entity.PhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.phone.PersonPhoneNumberRepositroy;
import ibee.webapp.todo_app.core.service.PhoneNumberServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.core.service.util.CompositeDependentEntityHandler;
import ibee.webapp.todo_app.mapper.person.contact.PersonPhoneNumberMapper;

@Service
@Transactional
public class PersonPhoneNumberServiceImpl
        extends PersonRelatedServiceImpl
            <PersonPhoneNumber, PersonPhoneNumberId> 
        implements CompositeDependentEntityHandler
            <PersonPhoneNumber, PersonPhoneNumberId, PhoneNumber, Long> {

    @Autowired 
    private PhoneNumberServiceImpl phoneNumberService;

    private final PersonPhoneNumberRepositroy repository;

    public PersonPhoneNumberServiceImpl(
        PersonPhoneNumberRepositroy repository,
        PersonPhoneNumberMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override 
    public PhoneNumber extractChild(PersonPhoneNumber parent) { 
        return parent.getPhoneNumber(); 
    }
    @Override 
    public void applyChild(PersonPhoneNumber parent, PhoneNumber child) { 
        parent.setPhoneNumber(child); 
    }
    @Override 
    public Long extractChildId(PhoneNumber child) { 
        return child.getId(); 
    }
    @Override 
    public Long extractChildIdFromComposite(PersonPhoneNumberId parentId) { 
        return parentId.getPhoneNumberId(); 
    }
    @Override 
    public PersonPhoneNumber instantiateNewParent() { 
        return new PersonPhoneNumber(); 
    }
    
    @Override 
    public PersonPhoneNumberId buildNewCompositeId(
        PersonPhoneNumberId oldId, Long newChildId) 
    { 
        return new PersonPhoneNumberId(oldId.getPersonId(), newChildId); 
    }
    
    @Override 
    public void applyCompositeId(PersonPhoneNumber parent, PersonPhoneNumberId id) { 
        parent.setId(id); 
    }

    @Override
    public PersonPhoneNumber create(PersonPhoneNumber entity) {
        assertData.entityNotNull(entity);
        
        validatePropertyPersonId(entity.getId());
        validateCompositeId(entity.getId(), "PersonPhoneNumberId");

        return resolveChildAndPersistNewLink(
                entity,
                phoneNumberService,
                (resolvedEntity) -> {
                    resolvedEntity.getId().setPhoneNumberId(
                        resolvedEntity.getPhoneNumber().getId());
                    return super.create(resolvedEntity);
                }
        );
    }

    @Override
    public PersonPhoneNumber update(PersonPhoneNumber incomingUpdates, PersonPhoneNumberId currentId) {
        Assert.notNull(incomingUpdates, "Update payload cannot be null");
        
        validateCompositeId(incomingUpdates.getId(), "Incoming PersonPhoneNumberId");
        validateCompositeId(currentId, "Current PersonPhoneNumberId");

        return resolveChildAndPersistUpdate(
                incomingUpdates,
                currentId,
                phoneNumberService,
                this::updateWithMainPhoneCleanup,  
                this::createWithMainPhoneCleanup,                              
                repository::findById,                     
                entityMapper::updateEntityFromEntity,     
                repository::deleteById   
        );
    }

    private void enforceOnlySingleMainPhoneNumberRule(PersonPhoneNumber entityUpdates) {
        Assert.notNull(
            entityUpdates, 
            "the incoming personPhoneNumber is not allowed to be Null"
        );

        if (entityUpdates.isMainPhoneNumber() == true) {
            validateCompositeId(
                entityUpdates.getId(), 
                "Business Rule PersonPhoneNumberId");

            Long personId = entityUpdates.getId().getPersonId();
            Long phoneId = entityUpdates.getId().getPhoneNumberId();

            repository.resetOtherMainPhoneNumbers(personId, phoneId);
        }
    }

    private PersonPhoneNumber updateWithMainPhoneCleanup(PersonPhoneNumber entity, PersonPhoneNumberId id) {
        enforceOnlySingleMainPhoneNumberRule(entity);
        return super.update(entity, id);
    }

    private PersonPhoneNumber createWithMainPhoneCleanup(PersonPhoneNumber entity) {
        enforceOnlySingleMainPhoneNumberRule(entity);
        return super.create(entity);
    }

    private void validatePropertyPersonId(PersonPhoneNumberId id) {
        Assert.notNull(id, "Id cannot be null");
        jakartaValidator.validateProperty(id, "personId", "Initial PersonId");
    }

    public Optional<PersonPhoneNumber> findMainPhoneNumber(Long personId) {
        return repository.findByPersonIdAndMainPhoneNumberTrue(personId);
    }
}
