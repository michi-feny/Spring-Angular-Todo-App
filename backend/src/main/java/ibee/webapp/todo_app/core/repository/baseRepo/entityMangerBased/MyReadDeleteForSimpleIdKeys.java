package ibee.webapp.todo_app.core.repository.baseRepo.entityMangerBased;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface MyReadDeleteForSimpleIdKeys<T> extends Repository<T, Long> {
 
    Optional<T> findById(Long id);
    Iterable<T> findAllById(Iterable<Long> ids);
 
    void deleteById(Long id);
    void delete(T entity);
    void deleteAllById(Iterable<? extends Long> ids);
    void deleteAll(Iterable<? extends T> entities);
     
}