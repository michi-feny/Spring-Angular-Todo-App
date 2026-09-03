package ibee.webapp.todo_app.core.repository.baseRepo.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;
import ibee.webapp.todo_app.core.repository.baseRepo.ParentRelatedEntity;

@NoRepositoryBean
public interface ParentRelatedRepository<ENTITY extends ParentRelatedEntity<PARENT_ID>, ID, PARENT_ID>
        extends MyFacadeBaseCrudRepository<ENTITY, ID> {

    // Concrete repos will write the specific @Query for these
    List<ID> findIdsByParentId(PARENT_ID parentId);
    List<ENTITY> findByParentId(PARENT_ID parentId);
    Optional<ENTITY> findWithDetailsById(ID id);
}
