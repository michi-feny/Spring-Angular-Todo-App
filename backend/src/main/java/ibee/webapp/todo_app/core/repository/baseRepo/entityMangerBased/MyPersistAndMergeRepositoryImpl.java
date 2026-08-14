package ibee.webapp.todo_app.core.repository.baseRepo.entityMangerBased;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class MyPersistAndMergeRepositoryImpl<T> implements MyPersistAndMergeRepository<T> {
 
    @PersistenceContext
    private EntityManager em;   
 
    @Override
    public <S extends T> void persist(S entity) {
        em.persist(entity);
    }
 
    @Override
    public <S extends T> void persistAll(Iterable<S> entities) {
        //List<S> result = new ArrayList<>();
        entities.forEach(e -> /*result.add(*/persist(e)/* )*/);
       // return result;
    }
 
    @Override
    public <S extends T> S merge(S entity) {
        return em.merge(entity);
    }
 
    @Override
    public <S extends T> List<S> mergeAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add(merge(e)));
        return result;
    }
}