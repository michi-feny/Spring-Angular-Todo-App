package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.hardSkills.ProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface ProfessionQualifacationRepository 
    extends MyBaseCrudRepo
        <ProfessionQualification, PersonProfessionQualificationId>{

}
