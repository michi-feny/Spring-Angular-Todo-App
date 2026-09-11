package ibee.webapp.todo_app.core.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import ibee.webapp.todo_app.core.entity.SoftSkill;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@Repository
public interface SoftSkillRepository 
    extends MyFacadeBaseCrudRepository
        <SoftSkill, Long>{

            /**
     * Spring Data JPA automatically parses this method name and creates 
     * a case-insensitive query against the "name" column!
     */
    Optional<SoftSkill> findByNameIgnoreCase(String name);

    
}
