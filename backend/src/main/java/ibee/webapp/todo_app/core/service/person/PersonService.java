package ibee.webapp.todo_app.core.service.person;

import java.util.Optional;

import ibee.webapp.todo_app.core.dto.person.PersonOverview;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.service.baseService.newApproach.BaseCrudService;

public interface PersonService
        extends BaseCrudService<Person, Long> {

    Optional<PersonOverview> findOverviewById(Long id);

    Optional<PersonData> findDataById(Long id);

    Optional<Person> findWithDetailsById(Long id);

    Person save(PersonForm form);
}