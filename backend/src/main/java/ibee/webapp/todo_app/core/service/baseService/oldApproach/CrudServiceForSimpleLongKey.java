package ibee.webapp.todo_app.core.service.baseService.oldApproach;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;


@Transactional
public interface CrudServiceForSimpleLongKey<T, ID> {

    List<T> getAll();
    Optional<T> getById(ID id);
    boolean existsById(ID id);
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
