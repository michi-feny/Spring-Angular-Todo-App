package ibee.webapp.todo_app.core.repository.person.personRelated.skill.softSkill;


import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonSoftSkillRepository 
    extends PersonRelatedRepository
        <PersonSoftSkill, PersonSoftSkillId>{

    @EntityGraph(attributePaths = {
        "softSkill"
    })
    Optional<PersonSoftSkill> findWithDetailsById(
        PersonSoftSkillId id
    );
}
