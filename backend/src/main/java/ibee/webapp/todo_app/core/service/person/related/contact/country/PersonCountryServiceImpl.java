package ibee.webapp.todo_app.core.service.person.related.contact.country;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.country.PersonCountryRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonCountryServiceImpl
        extends PersonRelatedServiceImpl<
                PersonCountry,
                PersonCountryId> {


    public PersonCountryServiceImpl(
            PersonCountryRepository repository) {

        super(repository);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonCountry> findWithDetailsById(PersonCountryId id) {
        return personRelatedRepository.findWithDetailsById(id);
    }

    

   

   
}
