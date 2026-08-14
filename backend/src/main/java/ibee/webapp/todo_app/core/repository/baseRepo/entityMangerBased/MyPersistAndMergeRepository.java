package ibee.webapp.todo_app.core.repository.baseRepo.entityMangerBased;

import java.util.List;

import jakarta.persistence.EntityExistsException;

public interface MyPersistAndMergeRepository<T> {
  
    /**
     * Save Operation, which dosnt look for existing db fields
     * no new id is given back
     * @throws EntityExistsException in case of entity dos not already exist
     * @param <S> type of entity to be persisted
     * @param entity entity name, which should be persisted
     */
    <S extends T> void persist(S entity);
    <S extends T> void persistAll(Iterable<S> entities);
    <S extends T> S merge(S entity);
    <S extends T> List<S> mergeAll(Iterable<S> entities);
}
