package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.professionQualification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonProfessionQualificationRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;

@Service
@Transactional
public class PersonProfessionQualificationServiceImpl
        extends PersonRelatedServiceImpl<PersonProfessionQualification, PersonProfessionQualificationId> {

    public PersonProfessionQualificationServiceImpl(PersonProfessionQualificationRepository repository) {
        super(repository);
    }

}
