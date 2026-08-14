package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.professionQualification;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonProfessionQualificationRepository;
import ibee.webapp.todo_app.core.service.baseService.person.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonProfessionService
        extends PersonRelatedServiceImpl<
                PersonProfessionQualification,
                PersonProfessionQualificationId> {

    private final PersonProfessionMapper mapper;

    public PersonProfessionService(
            PersonProfessionQualificationRepository repository,
            PersonProfessionMapper mapper) {

        super(repository);

        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PersonProfessionInfo> findInfoByPersonId(
            Long personId) {

        return findByPersonId(personId)
                .stream()
                .map(mapper::toInfo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PersonProfessionInfo> findInfoById(
            PersonProfessionQualificationId id) {

        return findWithDetailsById(id)
                .map(mapper::toInfo);
    }

    public PersonProfessionQualification save(
            PersonProfessionForm form) {

        return save(mapper.toEntity(form));
    }

    public PersonProfessionQualification update(
            PersonProfessionForm form) {

        return save(mapper.toEntity(form));
    }
}
