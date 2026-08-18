package ibee.webapp.todo_app.core.service.person;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.repository.person.PersonRepository;
import ibee.webapp.todo_app.core.repository.person.PersonSpecification;
import ibee.webapp.todo_app.core.service.baseService.newApproach.BaseCrudServiceImpl;

@Service
@Transactional
public class PersonServiceImpl 
        extends BaseCrudServiceImpl<Person, Long> 
        implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository repository) {
        super(repository);
        this.personRepository = repository;
    }

   @Override
    @Transactional(readOnly = true)
    public Optional<Person> findWithDetailsById(Long id) {
        return personRepository.findWithDetailsById(id);
    }
    // --- a) Einzelsuchen (Entity-Ebene) ---

    @Override
    @Transactional(readOnly = true)
    public List<Person> findByFirstName(String firstName) {
        return personRepository.findAll(PersonSpecification.filterBy(firstName, null, null, null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findByLastName(String lastName) {
        return personRepository.findAll(PersonSpecification.filterBy(null, lastName, null, null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findByBirthDate(LocalDate birthDate) {
        return personRepository.findAll(PersonSpecification.filterBy(null, null, birthDate, null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findBySocialRecordNumber(Short socialRecordNumber) {
        return personRepository.findAll(PersonSpecification.filterBy(null, null, null, socialRecordNumber));
    }

    // --- b) Kombinierte Suche (Entity-Ebene mit automatischer Null-Prüfung) ---

    @Override
    @Transactional(readOnly = true)
    public List<Person> findByFilter(String firstName, String lastName, LocalDate birthDate, Short socialRecordNumber) {
        return personRepository.findAll(PersonSpecification.filterBy(firstName, lastName, birthDate, socialRecordNumber));
    }

    @Override
    public Optional<Person> findDataById(Long id) {
        return personRepository.findById(id);
    }

    
}
