package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.additionalHardSkill;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonAdditionalHardSkillRepository;

@Service
@Transactional
public class PersonAdditionalSkillService
        extends PersonRelatedServiceImpl<
                PersonAdditionalHardSkill,
                PersonAdditionalHardSkillId> {

    private final PersonAdditionalSkillMapper mapper;

    public PersonAdditionalSkillService(
            PersonAdditionalHardSkillRepository repository,
            PersonAdditionalSkillMapper mapper) {

        super(repository);

        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PersonAdditionalSkillInfo> findInfoByPersonId(
            Long personId) {

        return findByPersonId(personId)
                .stream()
                .map(mapper::toInfo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PersonAdditionalSkillInfo> findInfoById(
            PersonAdditionalHardSkillId id) {

        return findWithDetailsById(id)
                .map(mapper::toInfo);
    }

    public PersonAdditionalHardSkill save(
            PersonAdditionalSkillForm form) {

        return save(mapper.toEntity(form));
    }

    public PersonAdditionalHardSkill update(
            PersonAdditionalSkillForm form) {

        return save(mapper.toEntity(form));
    }
}
