package ibee.webapp.todo_app.core.repository;

import ibee.webapp.todo_app.core.entity.hardSkills.AdditionalHardSkill;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;;

@Repository
public interface AdditionalHardSkillRepository
    extends MyFacadeBaseCrudRepository<AdditionalHardSkill, Long> {

    Optional<AdditionalHardSkill> findByNameAndLevel(String name, String level);
    
}
