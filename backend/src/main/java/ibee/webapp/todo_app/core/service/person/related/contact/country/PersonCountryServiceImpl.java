package ibee.webapp.todo_app.core.service.person.related.contact.country;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.repository.CountryRepository;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.country.PersonCountryRepository;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.contact.PersonCountryMapper;

@Service
@Transactional
public class PersonCountryServiceImpl extends PersonRelatedServiceImpl<PersonCountry, PersonCountryId> {

    private final PersonCountryRepository repository;
    
    @Autowired
    private CountryRepository countryRepository;

    public PersonCountryServiceImpl(PersonCountryRepository repository, PersonCountryMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public PersonCountry create(PersonCountry entity) {
        // Hydrate Country entity since it's master data
        Country country = countryRepository.findById(entity.getCountry().getId())
            .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        
        entity.setCountry(country);
        entity.getId().setCountryId(country.getId());

        if (entity.isMainCountry()) {
            repository.resetOtherMainCountries(entity.getId().getPersonId(), entity.getId().getCountryId());
        }
        return super.create(entity);
    }

    @Override
    public PersonCountry update(PersonCountry incomingUpdates, PersonCountryId currentId) {
        // Handle immutable composite ID swap if the user changed the country
        if (!incomingUpdates.getCountry().getId().equals(currentId.getCountryId())) {
            PersonCountry existing = findById(currentId);
            repository.delete(existing);
            return create(incomingUpdates);
        }

        if (incomingUpdates.isMainCountry()) {
            repository.resetOtherMainCountries(incomingUpdates.getId().getPersonId(), incomingUpdates.getId().getCountryId());
        }
        return super.update(incomingUpdates, currentId);
    }

    public Optional<PersonCountry> findMainCountry(Long personId) {
        return repository.findByPersonIdAndMainCountryTrue(personId);
    }
}
