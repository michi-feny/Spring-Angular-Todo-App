package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.additionalHardSkill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonAdditionalHardSkillRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonAdditionalHardSkillMapper;

@Service
@Transactional
public class PersonAdditionalSkillServiceImpl
        extends PersonRelatedServiceImpl<PersonAdditionalHardSkill, PersonAdditionalHardSkillId> {

    public PersonAdditionalSkillServiceImpl(
        PersonAdditionalHardSkillRepository repository,
        PersonAdditionalHardSkillMapper mapper) {
        super(repository, mapper);
    }

    
}
