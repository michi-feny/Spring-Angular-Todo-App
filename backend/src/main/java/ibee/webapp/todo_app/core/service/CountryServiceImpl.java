package ibee.webapp.todo_app.core.service;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.repository.AddressRepository;
import ibee.webapp.todo_app.core.repository.CountryRepository;
import ibee.webapp.todo_app.core.service.baseService.newApproach.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.CountryMapper;

@Service
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
}
