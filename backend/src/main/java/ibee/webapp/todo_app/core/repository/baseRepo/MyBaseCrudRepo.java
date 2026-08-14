package ibee.webapp.todo_app.core.repository.baseRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.repository.NoRepositoryBean;


/*
    Falls die ID im Code erzeugt wird, so ist dringend vom gebrauch dieses Interfaces abzuraten,
    da save operationen, davon ausgehen, dass es bereits eine db eintrag geben kann..
    dadurch wird dann zunächst dieser gesucht den es aber noch gar nicht gibt!
    SelfDefinedId id = new UUID(); --> id != null --> select statement 
    wird unnötig für einen persist aufgerufen

    Es besteht kein Problem, wenn die selbst definierte Id mit Hibernate erzeugt wird:
    Beispiel:
    @Id
    @GeneratedValue
    private UUID id;
*/
@NoRepositoryBean
public interface MyBaseCrudRepo<ENTITY,ID> extends Repository<ENTITY, ID>{
    <S extends ENTITY> S save(S entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> entities);
    Optional<ENTITY> findById(ID id);
    List<ENTITY> findAllById(Iterable<ID> ids);
    List<ENTITY> findAll();
    boolean existsById(ID id);
 
    /**
	 * Deletes the entity with the given id.
	 * <p>
	 * If the entity is not found in the persistence store it is silently ignored.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
	 * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
	 *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *           present but does not exist in the database.
	 */
    void deleteById(ID id);
    void delete(ENTITY entity);
    void deleteAllById(Iterable<? extends ID> ids);
    void deleteAll(Iterable<? extends ENTITY> entities);
    

}
