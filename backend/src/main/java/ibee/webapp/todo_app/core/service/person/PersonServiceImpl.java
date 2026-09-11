package ibee.webapp.todo_app.core.service.person;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.repository.person.PersonRepository;
import ibee.webapp.todo_app.core.repository.person.PersonSpecification;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.person.PersonMapper;

@Service
@Transactional
public class PersonServiceImpl 
        extends MyCrudBaseEntityFacedeServiceImpl<Person, Long> 
        implements PersonService {

    private final PersonRepository personRepository;
    //private final PersonMapper mapper;

    public PersonServiceImpl(
        PersonRepository repository, 
        PersonMapper mapper
    ) {
        super(repository, mapper);
        this.personRepository = repository;
        //this.mapper = mapper;
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
    public List<Person> findBySocialRecordNumber(Long socialRecordNumber) {
        return personRepository.findAll(PersonSpecification.filterBy(null, null, null, socialRecordNumber));
    }

    // --- b) Kombinierte Suche (Entity-Ebene mit automatischer Null-Prüfung) ---

    @Override
    @Transactional(readOnly = true)
    public List<Person> findByFilter(String firstName, String lastName, LocalDate birthDate, Long socialRecordNumber) {
        return personRepository.findAll(PersonSpecification.filterBy(firstName, lastName, birthDate, socialRecordNumber));
    }

    @Override
    public Optional<Person> findDataById(Long id) {
        return personRepository.findById(id);
    }

    
}
