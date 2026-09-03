package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonDegreeRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonDegreeMapper;

@Service
@Transactional
public class PersonDegreeServiceImpl
        extends PersonRelatedServiceImpl<PersonDegree, PersonDegreeId> {

    public PersonDegreeServiceImpl(
        PersonDegreeRepository repository,
        PersonDegreeMapper mapper) {
        super(repository, mapper);
    }

  /* * @Override
    @Transactional(readOnly = true)
    public Optional<PersonDegree> findWithDetailsById(PersonDegreeId id) {
        return personRelatedRepository.findWithDetailsById(id);
    }
        */
}
