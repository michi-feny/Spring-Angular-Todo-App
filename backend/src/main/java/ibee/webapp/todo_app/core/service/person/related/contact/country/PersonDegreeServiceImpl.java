package ibee.webapp.todo_app.core.service.person.related.contact.country;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonDegreeRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonDegreeServiceImpl
        extends PersonRelatedServiceImpl<
                PersonDegree,
                PersonDegreeId> {


    public PersonDegreeServiceImpl(
            PersonDegreeRepository repository) {

        super(repository);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonDegree> findWithDetailsById(PersonDegreeId id) {
        return personRelatedRepository.findWithDetailsById(id);
    }

    

   

   
}
