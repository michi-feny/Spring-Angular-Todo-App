package ibee.webapp.todo_app.core.service.person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.service.baseService.newApproach.BaseCrudService;

public interface PersonService
        extends BaseCrudService<Person, Long> {

    Optional<Person> findDataById(Long id);

    Optional<Person> findWithDetailsById(Long id);


    // a) Einzelne Feldsuchen (Entity-Ebene)
    List<Person> findByFirstName(String firstName);

    List<Person> findByLastName(String lastName);

    List<Person> findByBirthDate(LocalDate birthDate);

    List<Person> findBySocialRecordNumber(Short socialRecordNumber);

    // b) Kombinierte Suche (Entity-Ebene mit Null-Prüfung)
    List<Person> findByFilter(String firstName, String lastName, LocalDate birthDate, Short socialRecordNumber);



    
}