package ibee.webapp.todo_app.core.service.person.related.contact.address;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.address.PersonAddressRepository;
import ibee.webapp.todo_app.core.service.AddressServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.core.service.util.CompositeDependentEntityHandler;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;


@Service
@Transactional
public class PersonAddressServiceImpl
        extends PersonRelatedServiceImpl
            <PersonAddress, PersonAddressId> 
        implements CompositeDependentEntityHandler
            <PersonAddress, PersonAddressId, Address, Long> {

    @Autowired 
    private AddressServiceImpl addressService;

    
    private final PersonAddressRepository repository;

    public PersonAddressServiceImpl(
        PersonAddressRepository repository,
        PersonAddressMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override 
    public Address extractChild(PersonAddress parent) { 
        return parent.getAddress(); 
    }
    @Override 
    public void applyChild(PersonAddress parent, Address child) { 
        parent.setAddress(child); 
    }
    @Override 
    public Long extractChildId(Address child) { 
        return child.getId(); 
    }
    @Override 
    public Long extractChildIdFromComposite(PersonAddressId parentId) { 
        return parentId.getAddressId(); 
    }
    @Override 
    public PersonAddress instantiateNewParent() { 
        return new PersonAddress(); }
    
    @Override 
    public PersonAddressId buildNewCompositeId(
        PersonAddressId oldId, Long newChildId) 
    { 
        return new PersonAddressId(oldId.getPersonId(), newChildId); 
    }
    
    @Override 
    public void applyCompositeId(PersonAddress parent, PersonAddressId id) { 
        parent.setId(id); 
    }

    /**
     * Creates a new link between a Person and an Address, enforcing business rules 
     * and ensuring database-level deduplication.
     * <p>
     * This method intercepts the standard creation flow to perform two critical operations:
     * <ul>
     *     <li><b>Main Address Enforcement:</b> Ensures that if this new link is flagged as the 
     *     main address, any existing main address for this person is safely unset.</li>
     *     <li><b>Address Deduplication:</b> If an underlying {@link Address} payload is provided, 
     *     it is routed through the {@code AddressService} to guarantee we reuse an existing 
     *     address in the database rather than inserting a duplicate physical address row.</li>
     * </ul>
     *
     * @param entity The {@link PersonAddress} entity containing the link details and optional Address payload.
     * @return The newly persisted {@link PersonAddress} link.
     * @throws IllegalArgumentException if the entity or its composite ID is null.
     */
    @Override
    public PersonAddress create(PersonAddress entity) {
        assertData.entityNotNull(entity);
        
        validatePropertyPersonId(
                entity.getId()
        );

        validateCompositeId(
            entity.getId(),
            "PersonAddressId"
        );

        
        return resolveChildAndPersistNewLink(
                entity,
                addressService,
                (resolvedEntity) -> {
                    resolvedEntity.getId().setAddressId(
                        resolvedEntity.getAddress().getId());
                    return super.create(resolvedEntity);
                }
        );
    
    }

    

    /**
     * Updates an existing PersonAddress link, enforcing business rules and safely handling 
     * underlying address deduplication.
     * <p>
     * Because this entity uses a JPA Composite Key ({@link PersonAddressId}), its Primary Key 
     * is immutable. This method handles three distinct update scenarios:
     * <ul>
     *     <li><b>Metadata Only:</b> If only bridge fields (like {@code mainAddress}) are changed, 
     *     it performs a standard update.</li>
     *     <li><b>Safe Typo Fix:</b> If the address fields are changed but don't collide with 
     *     another existing address, the original address row is updated in place.</li>
     *     <li><b>Deduplication ID Swap:</b> If changing the address causes a collision with an 
     *     already existing address in the database, the {@code addressId} must change. To satisfy 
     *     JPA's immutable composite key constraint, the old link is deleted and a completely new 
     *     link is created.</li>
     * </ul>
     *
     * @param incomingUpdates The payload containing the new values to apply.
     * @param currentId       The current composite key (Person ID, Address ID) of the link being updated.
     * @return The updated or newly created {@link PersonAddress} entity.
     * @throws IllegalArgumentException if the payloads or IDs are null.
     */
    @Override
    public PersonAddress update(PersonAddress incomingUpdates, PersonAddressId currentId){

        Assert.notNull(incomingUpdates, "Update payload cannot be null");
        
        // Fail fast: Validate both keys using the dedicated helper
        validateCompositeId(incomingUpdates.getId(), "Incoming PersonAddressId");
        validateCompositeId(currentId, "Current PersonAddressId");

        return resolveChildAndPersistUpdate(
                incomingUpdates,
                currentId,
                addressService,
                this::updateWithMainAddressCleanup,  // <-- Custom Action
                this::createWithMainAddressCleanup,  // <-- Custom Action                            
                repository::findById,     
                //TODO: find by id maybe hibernate loads some lasy shit that way                
                entityMapper::updateEntityFromEntity,     
                repository::deleteById   
        );
       
    }
    

    /**
     * BUSINESS RULE: A person can only have one main address at a time.
     */
    private void enforceOnlySingleMainAddressRule(PersonAddress entityUpdates) {
        // If the incoming data wants this address to be the main one...
        Assert.notNull(
            entityUpdates, 
            "the incomming personAddress is not allowed to be Null"
        );

        if (entityUpdates.isMainAddress() == true) {
            // DEFENSIVE PROGRAMMING: Guarantee the ID is fully valid before running the DB query
            validateCompositeId(
                entityUpdates.getId(), 
                "Business Rule PersonAddressId");

            Long personId = entityUpdates.getId().getPersonId();
            Long addressId = entityUpdates.getId().getAddressId();

            // ...immediately set all OTHER addresses for this person to false in the DB!
            repository.resetOtherMainAddresses(personId, addressId);
        }
    }

    // ========================================================
    // Custom Actions (Database Consistency logic lives here!)
    // ========================================================
    
    private PersonAddress updateWithMainAddressCleanup(PersonAddress entity, PersonAddressId id) {
        enforceOnlySingleMainAddressRule(entity);
        return super.update(entity, id);
    }

    private PersonAddress createWithMainAddressCleanup(PersonAddress entity) {
        enforceOnlySingleMainAddressRule(entity);
        return super.create(entity);
    }

    

    private void validatePropertyPersonId(PersonAddressId id){
        Assert.notNull(id,  "Id cannot be null");
        jakartaValidator.validateProperty(id, "personId", "Initial PersonId");

    }

    public Optional<PersonAddress> findMainAddress(Long personId) {
        return repository.findByPersonIdAndMainAddressTrue(personId);
    }
   
}
