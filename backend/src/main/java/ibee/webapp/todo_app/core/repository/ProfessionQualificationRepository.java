package ibee.webapp.todo_app.core.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.hardSkills.ProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@Repository
public interface ProfessionQualificationRepository 
    extends MyFacadeBaseCrudRepository
        <ProfessionQualification, Long>{

    // Assuming HardSkill has a 'name' field
    Optional<ProfessionQualification> findByNameAndWeight(String name, Integer weight);
    
}
