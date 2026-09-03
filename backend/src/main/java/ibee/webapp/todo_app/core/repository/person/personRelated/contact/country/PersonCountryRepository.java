package ibee.webapp.todo_app.core.repository.person.personRelated.contact.country;


import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;

@Repository
public interface PersonCountryRepository
        extends PersonRelatedRepository<
            PersonCountry,
            PersonCountryId> {

    @EntityGraph(attributePaths = {
        "country"
    })
    Optional<PersonCountry> findWithDetailsById(
        PersonCountryId id
    );

    @Modifying
@Query("UPDATE PersonCountry pc SET pc.mainCountry = false WHERE pc.id.personId = :personId AND pc.id.countryId <> :countryId")
void resetOtherMainCountries(@Param("personId") Long personId, @Param("countryId") Long countryId);
}
