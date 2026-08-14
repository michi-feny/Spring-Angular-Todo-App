package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonDegreeRepository;
import ibee.webapp.todo_app.core.service.baseService.person.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonDegreeServiceImpl
        extends PersonRelatedServiceImpl<PersonDegree, PersonDegreeId>
        implements PersonDegreeService {

    private final PersonDegreeRepository repository;

    public PersonDegreeServiceImpl(
            PersonDegreeRepository repository
    ) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonDegree> findWithDetailsById(
            PersonDegreeId id
    ) {
        return repository.findWithDetailsById(id);
    }
}
