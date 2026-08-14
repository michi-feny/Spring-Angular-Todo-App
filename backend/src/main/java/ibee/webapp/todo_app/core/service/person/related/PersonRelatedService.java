package ibee.webapp.todo_app.core.service.person.related;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.newApproach.BaseCrudService;

public interface PersonRelatedService<
        ENTITY extends PersonRelatedEntity,
        ID>
        extends BaseCrudService<ENTITY, ID> {

    List<ID> findIdsByPersonId(Long personId);

    List<ENTITY> findByPersonId(Long personId);

    Optional<ENTITY> findWithDetailsById(ID id);
}
