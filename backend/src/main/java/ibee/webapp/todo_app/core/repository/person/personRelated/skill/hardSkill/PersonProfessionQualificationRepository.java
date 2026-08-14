package ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonProfessionQualificationRepository 
    extends PersonRelatedRepository
        <PersonProfessionQualification, PersonProfessionQualificationId>{

    @EntityGraph(attributePaths = {
        "professionQualification",
        "educationInstitution",
        "educationInstitution.address",
        "educationInstitution.address.country"
    })
    Optional<PersonProfessionQualification> findWithDetailsById(
        PersonProfessionQualificationId id
    );
}
