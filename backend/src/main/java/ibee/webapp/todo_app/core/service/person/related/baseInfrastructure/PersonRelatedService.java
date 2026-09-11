package ibee.webapp.todo_app.core.service.person.related.baseInfrastructure;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacadeService;

public interface PersonRelatedService
        <ENTITY extends PersonRelatedEntity,
        ID>
        extends MyCrudBaseEntityFacadeService<ENTITY, ID> {

    List<ID> findIdsByPersonId(Long personId);

    List<ENTITY> findByPersonId(Long personId);

    ENTITY findWithDetailsById(ID id);

    Optional<ENTITY> findWithDetailsByIdWithoutException(ID id);
}
