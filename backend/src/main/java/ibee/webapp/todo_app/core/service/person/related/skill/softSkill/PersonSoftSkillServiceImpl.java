package ibee.webapp.todo_app.core.service.person.related.skill.softSkill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.softSkill.PersonSoftSkillRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonSoftSkillServiceImpl
        extends PersonRelatedServiceImpl<PersonSoftSkill, PersonSoftSkillId> {

    public PersonSoftSkillServiceImpl(PersonSoftSkillRepository repository) {
        super(repository);
    }

}
