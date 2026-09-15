package ibee.webapp.todo_app.core.service.baseService.persist;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;

import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;

@Validated
public interface MyCrudBaseEntityFacadeService<ENTITY, ID> {

    // ENTITY create(@Validated(OnCreate.class) ENTITY entity);

    // ENTITY update(@Validated(OnUpdate.class) ENTITY entity, ID id);

    ENTITY create(
        @Validated(OnCreate.class) ENTITY entity);

    ENTITY update(
        @Validated(OnUpdate.class) ENTITY entity,
        ID id
    );

    List<ENTITY> saveAll(
        @Validated(OnCreate.class) Iterable<ENTITY> entities);

    ENTITY findById(ID id);

    Optional<ENTITY> findByIdWithoutException(ID id);

    List<ENTITY> findAllById(Iterable<ID> ids);

    List<ENTITY> findAll();

    boolean existsById(ID id);

    void deleteById(ID id);

    void delete(ENTITY entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends ENTITY> entities);
}
