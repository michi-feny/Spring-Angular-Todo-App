package ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonDegreeRepository
        extends PersonRelatedRepository
                <PersonDegree, PersonDegreeId> {


    @Override 
    @EntityGraph(attributePaths = {

            "degree",

            "educationInstitution",
            "educationInstitution.address",
            "educationInstitution.address.country"

    })
    Optional<PersonDegree> findWithDetailsById(PersonDegreeId id);


    @Override 
    @EntityGraph(attributePaths = {

            "degree",

            "educationInstitution",
            "educationInstitution.address",
            "educationInstitution.address.country"

    })
    List<PersonDegree> findWithDetailsByPersonId(Long personId);

    
    @EntityGraph(attributePaths = {
            "degree"
    })
    Optional<PersonDegree> findWithOnlyDegreeDetailsById(PersonDegreeId id);

    @EntityGraph(attributePaths = {
            "degree"
    })
    Optional<PersonDegree> findWithOnlyDegreeDetailsByPersonId(Long id);


    



   
}
