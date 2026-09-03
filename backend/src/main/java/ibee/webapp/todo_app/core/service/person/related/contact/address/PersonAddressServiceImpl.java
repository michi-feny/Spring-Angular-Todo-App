package ibee.webapp.todo_app.core.service.person.related.contact.address;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.address.PersonAddressRepository;
import ibee.webapp.todo_app.core.service.AddressServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;


@Service
@Transactional
public class PersonAddressServiceImpl
        extends PersonRelatedServiceImpl<PersonAddress, PersonAddressId> {

    @Autowired
    private AddressServiceImpl addressService;

    
    private final PersonAddressRepository repository;

    public PersonAddressServiceImpl(
        PersonAddressRepository repository,
        PersonAddressMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
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
        
        Assert.notNull(
            entity, 
            "PersonAddress entity cannot be null");
        Assert.notNull(
            entity.getId(), 
            "PersonAddressId cannot be null for creation");
        

        /* 
         * SPLIT VALIDATION STAGE 1: FAIL FAST
         * Why: We cannot validate the AddressId yet because the user might be submitting 
         * a brand new address that hasn't been saved to the database. However, we must 
         * strictly validate the PersonId upfront to reject bad requests instantly.
         */
        validatePropertyPersonId(entity.getId());
        

        // 1. Resolve the address through deduplication and generate its new database ID
        if (entity.getAddress() != null) {
            Address safeAddress = addressService.create(entity.getAddress());
            entity.setAddress(safeAddress);
            
            // Mutate the existing ID object to attach the newly generated Address ID
            entity.getId().setAddressId(safeAddress.getId());
        }

        
        enforceOnlySingleMainAddressRule(entity);

        return super.create(entity);
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

        // Guard Clause 1: No Address payload. Safe to pass to base class.
        if (incomingUpdates.getAddress() == null) {
            enforceOnlySingleMainAddressRule(incomingUpdates);
            return super.update(incomingUpdates, currentId);
        }

        Address resolvedAddress = addressService.update(incomingUpdates.getAddress(), currentId.getAddressId());
        incomingUpdates.setAddress(resolvedAddress);

        // Guard Clause 2: Safe Typo Fix (ID stayed the same). Safe to pass to base class.
        if (resolvedAddress.getId().equals(currentId.getAddressId())) {
            enforceOnlySingleMainAddressRule(incomingUpdates);
            return super.update(incomingUpdates, currentId);
        }

        // ==========================================
        // THE FALLTHROUGH: DEDUPLICATION ID SWAP
        // ==========================================
        
        // 1. Fetch the ORIGINAL entity so we don't lose any existing historical data
        PersonAddress originalEntity = repository.findById(currentId)
            .orElseThrow(() -> new IllegalArgumentException("Original PersonAddress not found"));

        // 2. Create a BRAND NEW, untracked instance to avoid Hibernate proxy crashes
        PersonAddress newLink = new PersonAddress();
        
        /* 
         * STAGE 1: CLONE THE EXISTING DATABASE STATE
         * Why: The newLink is completely empty. The incoming payload might be a partial update 
         * missing background fields like auditing timestamps, versions, or untouched metadata.
         * Mapping the original entity into newLink ensures we preserve all existing data 
         * exactly as it currently exists in the database.
         */
        entityMapper.updateEntityFromEntity(originalEntity, newLink);
        
        /*
         * STAGE 2: OVERLAY THE USER'S REQUESTED CHANGES
         * Why: Now that newLink holds a safe copy of the full database row, we apply the 
         * delta/patch. Any non-null fields in incomingUpdates (e.g., a changed mainAddress boolean) 
         * will overwrite the cloned values.
         */
        entityMapper.updateEntityFromEntity(incomingUpdates, newLink);

        // 5. Manually force the NEW Composite Key using the All-Args Constructor
        PersonAddressId newId = 
            new PersonAddressId(
                currentId.getPersonId(), 
                resolvedAddress.getId()
            );
        
        newLink.setId(newId);
        newLink.setAddress(resolvedAddress);

        // 6. Delete the old row in the database
        repository.deleteById(currentId);

        // 7. Enforce the business rule on the final, perfectly mapped object
        enforceOnlySingleMainAddressRule(newLink);
        
        // 8. Save the brand new entity
        return super.create(newLink);
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

        if (entityUpdates.isMainAddress()) {
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

    

    private void validatePropertyPersonId(PersonAddressId id){
        Assert.notNull(id,  "Id cannot be null");
        jakartaValidator.validateProperty(id, "personId", "Initial PersonId");

    }

}
