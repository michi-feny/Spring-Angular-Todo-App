package ibee.webapp.todo_app.core.service.baseService.newApproach;

import java.util.List;
import java.util.Optional;

public interface BaseCrudService<ENTITY, ID> {

    ENTITY save(ENTITY entity);

    List<ENTITY> saveAll(Iterable<ENTITY> entities);

    Optional<ENTITY> findById(ID id);

    List<ENTITY> findAllById(Iterable<ID> ids);

    List<ENTITY> findAll();

    boolean existsById(ID id);

    void deleteById(ID id);

    void delete(ENTITY entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends ENTITY> entities);
}
