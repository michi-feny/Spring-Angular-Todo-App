package ibee.webapp.todo_app.core.service.person.related.contact.country;

import ibee.webapp.todo_app.core.service.CountryServiceImpl;
import ibee.webapp.todo_app.core.service.util.CompositeDependentEntityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.country.PersonCountryRepository;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.contact.PersonCountryMapper;
import org.springframework.util.Assert;
import java.util.Optional;

@Service
@Transactional
public class PersonCountryServiceImpl
        extends PersonRelatedServiceImpl<PersonCountry, PersonCountryId>
        implements CompositeDependentEntityHandler
        <PersonCountry, PersonCountryId, Country, Long> {

    private final PersonCountryRepository repository;

    @Autowired
    private CountryServiceImpl countryService;

    public PersonCountryServiceImpl(PersonCountryRepository repository, PersonCountryMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public Country extractChild(PersonCountry personCountry) {
        return personCountry != null ? personCountry.getCountry() : null;
    }

    @Override
    public void applyChild(PersonCountry personCountry, Country country) {
        personCountry.setCountry(country);
    }

    @Override
    public Long extractChildId(Country country) {
        return country != null ? country.getId() : null;
    }

    @Override
    public Long extractChildIdFromComposite(PersonCountryId personCountryId) {
        return personCountryId != null ? personCountryId.getCountryId() : null;
    }

    @Override
    public PersonCountryId buildNewCompositeId(PersonCountryId oldId, Long newChildId) {
        return new PersonCountryId(oldId != null ? oldId.getPersonId() : null, newChildId);
    }

    @Override
    public void applyCompositeId(PersonCountry personCountry, PersonCountryId personCountryId) {
        personCountry.setId(personCountryId); // KORREKTUR: setId anstelle von setCountry
    }

    @Override
    public PersonCountry instantiateNewParent() {
        return new PersonCountry();
    }

    @Override
    public PersonCountry create(PersonCountry entity) {
        assertData.entityNotNull(entity);
        validatePropertyPersonId(entity.getId());
        validateCompositeId(entity.getId(), "PersonCountryId");

        return resolveChildAndPersistNewLink(
                entity,
                countryService,
                (resolvedEntity) -> {
                    resolvedEntity.getId().setCountryId(resolvedEntity.getCountry().getId());
                    return createWithMainCountryCleanup(resolvedEntity);
                }
        );
    }

    @Override
    public PersonCountry update(PersonCountry incomingUpdates, PersonCountryId currentId) {
        Assert.notNull(incomingUpdates, "Update payload cannot be null");

        validateCompositeId(incomingUpdates.getId(), "Incoming PersonCountryId");
        validateCompositeId(currentId, "Current PersonCountryId");

        return resolveChildAndPersistUpdate(
                incomingUpdates,
                currentId,
                countryService,
                this::updateWithMainCountryCleanup,
                this::createWithMainCountryCleanup,
                repository::findById,
                entityMapper::updateEntityFromEntity,
                repository::deleteById
        );
    }

    private void enforceOnlySingleMainCountryRule(PersonCountry entityUpdates) {
        Assert.notNull(entityUpdates, "The incoming personCountry is not allowed to be Null");

        if (Boolean.TRUE.equals(entityUpdates.isMainCountry())) {
            validateCompositeId(entityUpdates.getId(), "Business Rule PersonCountryId");

            Long personId = entityUpdates.getId().getPersonId();
            Long countryId = entityUpdates.getId().getCountryId();

            repository.resetOtherMainCountries(personId, countryId);
        }
    }

    private PersonCountry updateWithMainCountryCleanup(PersonCountry entity, PersonCountryId id) {
        enforceOnlySingleMainCountryRule(entity);
        return super.update(entity, id);
    }

    private PersonCountry createWithMainCountryCleanup(PersonCountry entity) {
        enforceOnlySingleMainCountryRule(entity);
        return super.create(entity);
    }

    private void validatePropertyPersonId(PersonCountryId id) {
        Assert.notNull(id, "Id cannot be null");
        jakartaValidator.validateProperty(id, "personId", "Initial PersonId");
    }

    public Optional<PersonCountry> findMainCountry(Long personId) {
        return repository.findByPersonIdAndMainCountryTrue(personId);
    }
}