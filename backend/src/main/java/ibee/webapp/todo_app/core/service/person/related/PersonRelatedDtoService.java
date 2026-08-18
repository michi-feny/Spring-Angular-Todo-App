package ibee.webapp.todo_app.core.service.person.related;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.newApproach.CrudDtoService;

public interface PersonRelatedDtoService<
        DTO,
        ENTITY extends PersonRelatedEntity,
        ID,
        IDDTO>
    extends CrudDtoService<DTO, ID> {

    List<IDDTO> findIdsByPersonId(Long personId);
    List<DTO> findByPersonId(Long personId);
    Optional<DTO> findWithDetailsById(ID id);

    // optional: expose entity service when necessary
    //PersonRelatedService<ENTITY, ID> getEntityService();
}
