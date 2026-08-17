package ibee.webapp.todo_app.core.service.baseService.oldApproach;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;


@Transactional
public interface CrudServiceForSimpleLongKey<DTO, ENTITY, ID> {

    List<ENTITY> getAll();

    Optional<ENTITY> getById(ID id);

    Optional<DTO> getDtoById(ID id);

    boolean existsById(ID id);

    DTO createFromDto(DTO dto);

    DTO updateFromDto(ID id, DTO dto);

    ENTITY create(ENTITY entity);

    ENTITY update(ID id, ENTITY dto);

    void delete(ID id);
}
