package ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonDegreeRepository
        extends PersonRelatedRepository
                <PersonDegree, PersonDegreeId> {

    @EntityGraph(attributePaths = {

            "degree",

            "educationInstitution",
            "educationInstitution.address",
            "educationInstitution.address.country"

    })
    Optional<PersonDegree> findWithDetailsById(Long id);

    @EntityGraph(attributePaths = {

            "degree"

    })
    Optional<PersonDegree> findWithOnlyDegreeDetailsById(Long id);



   
}
