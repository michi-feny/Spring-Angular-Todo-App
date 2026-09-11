package ibee.webapp.todo_app.core.repository.person.personRelated.contact.phone;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonPhoneNumberRepositroy 
    extends PersonRelatedRepository
        <PersonPhoneNumber, PersonPhoneNumberId>{

    @EntityGraph(attributePaths = {
        "phoneNumber"
    })
    Optional<PersonPhoneNumber> findWithOnlyPhoneNumberDetailsById(
        PersonPhoneNumberId id
    );

    @EntityGraph(attributePaths = {
        "phoneNumber",
        "phoneNumber.country"
    })
    Optional<PersonPhoneNumber> findWithDetailsById(
        PersonPhoneNumberId id
    );

    /**
     * Finds the current main phone number for a specific person.
     */
    @Query("SELECT p FROM PersonPhoneNumber p WHERE p.id.personId = :personId AND p.mainPhoneNumber = true")
    Optional<PersonPhoneNumber> findByPersonIdAndMainPhoneNumberTrue(@Param("personId") Long personId);

    /**
     * BUSINESS RULE ENFORCEMENT:
     * Unsets the main flag for all OTHER phone numbers belonging to this person.
     * 
     * @param personId The ID of the person.
     * @param phoneId  The ID of the phone number that is becoming the NEW main number (so it is excluded from the reset).
     */
    @Modifying
    @Query("UPDATE PersonPhoneNumber p SET p.mainPhoneNumber = false WHERE p.id.personId = :personId AND p.id.phoneId != :phoneId")
    void resetOtherMainPhoneNumbers(@Param("personId") Long personId, @Param("phoneId") Long phoneId);

    
}
