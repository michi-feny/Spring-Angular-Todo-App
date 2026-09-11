package ibee.webapp.todo_app.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.repository.CountryRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.CountryMapper;

@Service
@Transactional
public class CountryServiceImpl 
    extends MyCrudBaseEntityFacedeServiceImpl<Country, Long> {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(
      CountryRepository repository,
      CountryMapper mapper
    ) {
        super(repository, mapper);
        this.countryRepository = repository;
    }   
    
    @Override
    public Country create(Country requestedCountry) {
        if (requestedCountry == null) {
            throw new IllegalArgumentException("Country cannot be null.");
        }

        // Intercept creation: Country is master data. 
        // We act as a deduplicator by simply returning the existing managed entity.
        if (requestedCountry.getId() != null) {
            Optional<Country> existing = countryRepository.findById(requestedCountry.getId());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        throw new IllegalStateException("Countrys must alredy be Present inside DB - Check for dbSeeder!");
    }
    
    @Override
    public Country update(Country sourceUpdates, Long id) {
        if (sourceUpdates == null || id == null) {
            throw new IllegalArgumentException("Country or its id cannot be null.");
        }
        // Same for update, ensure we return the managed master data
        Optional<Country> existing = countryRepository.findById(id);
        if (existing.isPresent()) {
            return existing.get();
        }
        throw new IllegalStateException("Countrys must alredy be Present inside DB - Check for dbSeeder!");
    }
}
