package ibee.webapp.todo_app.core.service.person.related.contact.country;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.country.PersonCountryRepository;
import ibee.webapp.todo_app.core.service.CountryServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.contact.PersonCountryMapper;
import io.jsonwebtoken.lang.Assert;

@Service
@Transactional
public class PersonCountryServiceImpl
        extends PersonRelatedServiceImpl<
                PersonCountry,
                PersonCountryId> {

    @Autowired
    private CountryServiceImpl countryService;

    private final PersonCountryRepository repository;

    public PersonCountryServiceImpl(
            PersonCountryRepository repository,
            PersonCountryMapper mapper
        ) {
        this.repository = repository;
        super(repository, mapper);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonCountry> findWithDetailsById(PersonCountryId id) {
        return repository.findWithDetailsById(id);
    }

    /**
 * Resets the mainCountry flag to false for all other countries linked to this person,
 * ensuring only one main country remains active.
 */
public void resetOtherMainCountries(Long personId, Long countryId) {
    Assert.notNull(personId, "personId cannot be null");
    Assert.notNull(countryId, "countryId cannot be null");
    
    // Assuming your repository is injected or accessible via casting if using a generic base repo
    repository.resetOtherMainCountries(personId, countryId);
}

    
@Override
    public PersonCountry create(PersonCountry entity) {
       Assert.notNull(entity, "PersonCountry entity cannot be null");
        Assert.notNull(entity.getId(), "PersonCountryId cannot be null for creation");

        validatePropertyPersonId(entity.getId());
        validatePropertyCountryId(entity.getId());

        Country existingCountry = countryService.findById(entity.getId().getCountryId())
            .orElseThrow(() -> new IllegalArgumentException("Country not found with ID: " + entity.getId().getCountryId()));
        entity.setCountry(existingCountry);

        validateCompositeId(entity.getId(), "Final PersonCountryId");

        enforceOnlySingleMainCountryRule(entity);
        return super.create(entity);
    }

    @Override
    public PersonCountry update(PersonCountry incomingUpdates, PersonCountryId currentId) {
        // Fail fast: Ensure the incoming patch payload is present before attempting any operations.
        Assert.notNull(incomingUpdates, "Update payload cannot be null");
        
        // Validate both the incoming identifier payload and the existing target identifier structurally.
        validateCompositeId(incomingUpdates.getId(), "Incoming PersonCountryId");
        validateCompositeId(currentId, "Current PersonCountryId");

        // Reference Resolution: Since Country is a static state/lookup entity that must NEVER 
        // be updated, mutated, or created via user workflows, we fetch the existing database 
        // instance to link it safely without altering the master Country record itself.
        if (incomingUpdates.getCountry() != null && incomingUpdates.getCountry().getId() != null) {
            Country resolvedCountry = countryService.findById(incomingUpdates.getId().getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found with ID: " + incomingUpdates.getId().getCountryId()));
            incomingUpdates.setCountry(resolvedCountry);
        }

        // SCENARIO 1: Metadata Patch (The Country ID remains unchanged).
        // Since the composite primary key identifier components remain identical, we enforce 
        // the single main country rule and delegate directly to the base service's update method 
        // (`super.update`), leveraging your MapStruct internal entity update mapping.
        if (incomingUpdates.getId().getCountryId().equals(currentId.getCountryId())) {
            enforceOnlySingleMainCountryRule(incomingUpdates);
            return super.update(incomingUpdates, currentId);
        }

        // SCENARIO 2: Relationship Swap (The Country reference itself is changing).
        // JPA composite keys (`@EmbeddedId`) are immutable. You cannot modify fields inside an active 
        // identifier directly. We must handle this via a delete-and-recreate lifecycle swap.

        // 1. Fetch the original persisted link to preserve historical metadata and auditing values.
        PersonCountry originalEntity = repository.findById(currentId)
            .orElseThrow(() -> new IllegalArgumentException("Original PersonCountry not found"));

        // 2. Instantiate a fresh, untracked target entity to prevent Hibernate proxy/persistence conflicts.
        PersonCountry newLink = new PersonCountry();
        
        // 3. Map base database state first so we don't lose untouched historical metadata fields.
        entityMapper.updateEntityFromEntity(originalEntity, newLink);
        
        // 4. Overlay the user's incoming updates/deltas onto the fresh entity clone using your mapper.
        entityMapper.updateEntityFromEntity(incomingUpdates, newLink);

        // 5. Construct the brand new immutable composite key combining the persistent Person ID 
        // with the newly resolved Country ID reference.
        PersonCountryId newId = new PersonCountryId(currentId.getPersonId(), incomingUpdates.getCountry().getId());
        newLink.setId(newId);

        // 6. Explicitly delete the old composite key row from the junction table to avoid primary key collisions.
        repository.deleteById(currentId);

        // 7. Enforce the business invariant (only a single main country can be active per person).
        enforceOnlySingleMainCountryRule(newLink);
        
        // 8. Persist the newly constructed link row using the creation pipeline.
        return super.create(newLink);
    }

    private void enforceOnlySingleMainCountryRule(PersonCountry entityUpdates) {
        Assert.notNull(entityUpdates, "The incoming PersonCountry is not allowed to be null");

        if (entityUpdates.isMainCountry()) {
            validateCompositeId(entityUpdates.getId(), "Business Rule PersonCountryId");

            Long personId = entityUpdates.getId().getPersonId();
            Long countryId = entityUpdates.getId().getCountryId();

            resetOtherMainCountries(personId, countryId);
        }
    }

    

    @Override
    protected void validateCompositeId(PersonCountryId id, String contextName) {
        super.validateCompositeId(id, contextName);
    }

    private void validatePropertyPersonId(PersonCountryId id) {
        Assert.notNull(id, "Id cannot be null");
        jakartaValidator.validateProperty(id, "personId", "Initial PersonId");
    }

    private void validatePropertyCountryId(PersonCountryId id) {
        Assert.notNull(id, "Id cannot be null");
        jakartaValidator.validateProperty(id, "countryId", "Initial CountryId");
    }


   

   
}
