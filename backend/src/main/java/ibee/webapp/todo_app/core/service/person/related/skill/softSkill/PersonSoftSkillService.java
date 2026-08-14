package ibee.webapp.todo_app.core.service.person.related.skill.softSkill;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.softSkill.PersonSoftSkillRepository;

@Service
@Transactional
public class PersonSoftSkillService
        extends PersonRelatedServiceImpl<
                PersonSoftSkill,
                PersonSoftSkillId> {

    private final PersonSoftSkillMapper mapper;

    public PersonSoftSkillService(
            PersonSoftSkillRepository repository,
            PersonSoftSkillMapper mapper) {

        super(repository);

        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PersonSoftSkillInfo> findInfoByPersonId(
            Long personId) {

        return findByPersonId(personId)
                .stream()
                .map(mapper::toInfo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PersonSoftSkillInfo> findInfoById(
            PersonSoftSkillId id) {

        return findWithDetailsById(id)
                .map(mapper::toInfo);
    }

    public PersonSoftSkill save(
            PersonSoftSkillForm form) {

        return save(mapper.toEntity(form));
    }

    public PersonSoftSkill update(
            PersonSoftSkillForm form) {

        return save(mapper.toEntity(form));
    }
}
