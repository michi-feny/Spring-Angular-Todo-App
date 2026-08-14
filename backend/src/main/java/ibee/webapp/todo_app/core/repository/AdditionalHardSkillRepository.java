package ibee.webapp.todo_app.core.repository;

import ibee.webapp.todo_app.core.entity.hardSkills.AdditionalHardSkill;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

import org.springframework.stereotype.Repository;;

@Repository
public interface AdditionalHardSkillRepository
    extends MyBaseCrudRepo<AdditionalHardSkill, Long> {

}
