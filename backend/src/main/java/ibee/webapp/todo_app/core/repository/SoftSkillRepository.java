package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;
import ibee.webapp.todo_app.core.entity.SoftSkill;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface SoftSkillRepository 
    extends MyBaseCrudRepo
        <SoftSkill, Long>{
}
