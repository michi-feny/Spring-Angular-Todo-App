package ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonAdditionalHardSkillRepository 
    extends PersonRelatedRepository
        <PersonAdditionalHardSkill, PersonAdditionalHardSkillId>{

    @EntityGraph(attributePaths = {
        "additionalHardSkill"
    })
    Optional<PersonAdditionalHardSkill> findWithDetailsById(
        PersonAdditionalHardSkillId id
    );
}
