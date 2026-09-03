package ibee.webapp.todo_app.core.entity.person;

import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.repository.baseRepo.IdentifiableEntity;

public interface PersonRelatedEntity<ID> extends IdentifiableEntity<ID>{
    Person getPerson();

    void setPerson(Person person);

}
