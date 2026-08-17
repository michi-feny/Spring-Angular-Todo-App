package ibee.webapp.todo_app.core.repository.person;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.dto.person.PersonOverview;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface PersonRepository extends MyBaseCrudRepo<Person, Long>{

    @EntityGraph(attributePaths = {

            //CONTACT DETAILS

                //NATIONALITY
                "nationalitys",
                "nationalitys.country",

                // Addresses
                "addresses",
                "addresses.address",
                "addresses.address.country",

                // Phone numbers
                "phones",
                "phones.phoneNumber",

                // email adress
                "emails",
                "emails.emailAddress",

            //SKILLS
            
                //HARD SKILLS

                    // Degrees
                    "degrees",
                    "degrees.degree",
                    "degrees.educationInstitution",
                    "degrees.educationInstitution.address",
                    "degrees.educationInstitution.address.country",

                    //ProfessionQualification
                    "professions",
                    "professions.professionQualification",
                    "professions.educationInstitution",
                    "professions.educationInstitution.address",
                    "professions.educationInstitution.address.country",

                    //ADDITIONAL HARD SKILLS
                    "additionalSkills",
                    "additionalSkills.additionalHardSkill",

                //SOFT SKILLS

                "softSkills",
                "softSkills.softSkill"

    })
    Optional<Person> findWithDetailsById(Long id);


}
