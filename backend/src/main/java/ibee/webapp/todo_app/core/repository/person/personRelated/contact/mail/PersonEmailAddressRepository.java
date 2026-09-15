package ibee.webapp.todo_app.core.repository.person.personRelated.contact.mail;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonEmailAddressRepository 
    extends PersonRelatedRepository
        <PersonEmailAddress, PersonEmailAddressId>{


    @EntityGraph(attributePaths = {
        "emailAddress"
    })
    Optional<PersonEmailAddress> findWithDetailsById(
        PersonEmailAddressId id
    );

    // Eagerly fetches the nested 'emailAddress' to prevent LazyInitializationException
    // when mapping the list of PersonEmailAddress entities to DTOs
    @EntityGraph(attributePaths = {"emailAddress"})
    List<PersonEmailAddress> findByPersonId(Long personId);
    
    /**
     * Finds the current main email address for a specific person.
     */
    @Query("SELECT p FROM PersonEmailAddress p WHERE p.id.personId = :personId AND p.mainEmail = true")
    Optional<PersonEmailAddress> findByPersonIdAndMainEmailAddressTrue(@Param("personId") Long personId);

    /**
     * BUSINESS RULE ENFORCEMENT:
     * Unsets the main flag for all OTHER email addresses belonging to this person.
     * 
     * @param personId The ID of the person.
     * @param emailId  The ID of the email address that is becoming the NEW main email (so it is excluded from the reset).
     */
    @Modifying
    @Query("UPDATE PersonEmailAddress p SET p.mainEmail = false WHERE p.id.personId = :personId AND p.id.emailAddressId != :emailId")
    void resetOtherMainEmailAddresses(@Param("personId") Long personId, @Param("emailId") Long emailId);
}
