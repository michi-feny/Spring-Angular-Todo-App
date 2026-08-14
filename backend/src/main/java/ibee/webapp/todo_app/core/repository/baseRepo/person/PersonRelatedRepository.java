package ibee.webapp.todo_app.core.repository.baseRepo.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@NoRepositoryBean
public interface PersonRelatedRepository
    <ENTITY extends PersonRelatedEntity, ID>
        extends MyBaseCrudRepo<ENTITY, ID> {


    @Query("""
        SELECT e.id
        FROM #{#entityName} e
        WHERE e.person.id = :personId
    """)
    List<ID> findIdsByPersonId(
        @Param("personId") Long personId
    );
    Optional<ENTITY> findWithDetailsById(ID id);


}
