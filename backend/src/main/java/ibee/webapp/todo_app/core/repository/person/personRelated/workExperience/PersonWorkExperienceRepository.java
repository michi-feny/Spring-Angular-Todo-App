package ibee.webapp.todo_app.core.repository.person.personRelated.workExperience;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;
/**
 * Repository interface for managing persistent operations on {@link PersonWorkExperience} entities.
 * Provides custom queries for visibility filtering, cascading link updates, order shifting, and sequence validation.
 */
@Repository
public interface PersonWorkExperienceRepository 
    extends PersonRelatedRepository<PersonWorkExperience, PersonWorkExperienceId>{



    @Override
    @EntityGraph(attributePaths = {
            "workExperience",                  // Core payload
            "subExperiences",                  // Rule 1: Include subExperiences
            "subExperiences.workExperience"    // Payload for the children
            
            // Rule 3: "mergedInto" is INTENTIONALLY LEFT OUT to avoid circular fetching
    })
    Optional<PersonWorkExperience> findWithDetailsById(PersonWorkExperienceId id);

    // 2. Fetch all records for the person
    @Override
    @EntityGraph(attributePaths = {
            "workExperience",
            "subExperiences",
            "subExperiences.workExperience"
    })
    List<PersonWorkExperience> findWithDetailsByPersonId(Long personId);

    // 3. OPTIONAL BUT RECOMMENDED: Fetch ONLY the root nodes for clean UI rendering
    @EntityGraph(attributePaths = {
            "workExperience",
            "subExperiences",
            "subExperiences.workExperience"
    })
    List<PersonWorkExperience> findRootsByPersonIdAndMergedIntoIsNull(Long personId);

    // Fetch only visible entries for a specific person (hiding merged sub-records)
    /**
     * Fetches only active, visible work experience entries for a specific person, filtering out merged sub-records.
     * 
     * @param personId the unique identifier of the person
     * @return a list of visible person work experience entities
     */
    @Query("""
        SELECT pwe 
        FROM PersonWorkExperience pwe 
        WHERE pwe.id.personId = :personId 
          AND pwe.visible = true
    """)List<PersonWorkExperience> 
    findVisibleByPersonId(@Param("personId") Long personId);


    /**
     * Soft-deletes sub-records by marking them invisible and linking them to a new master work experience record.
     * 
     * @param personId        the unique identifier of the person
     * @param subWorkExpIds   the list of work experience IDs to hide and link
     * @param masterWorkExpId the identifier of the target master record
     * @return the number of updated database rows
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE PersonWorkExperience p 
        SET p.visible = false, p.mergedIntoWorkExpId = :masterWorkExpId 
        WHERE p.id.personId = :personId 
          AND p.id.workExperienceId IN :subWorkExpIds
    """)
    int hideAndLinkSubRecords(@Param("personId") Long personId, 
                              @Param("subWorkExpIds") List<Long> subWorkExpIds, 
                              @Param("masterWorkExpId") Long masterWorkExpId);

    /**
     * Shifts display orders left in bulk to close sequence gaps after deletions or merges.
     * 
     * @param personId             the unique identifier of the person
     * @param targetDisplayOrder   the threshold display order position
     * @param shiftAmount          the numeric value to decrement the display order by
     * @return the number of updated database rows
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE PersonWorkExperience p 
        SET p.displayOrder = p.displayOrder - :shiftAmount 
        WHERE p.id.personId = :personId 
          AND p.displayOrder > :targetDisplayOrder
    """)
    int shiftDisplayOrdersAfterMerge(@Param("personId") Long personId, 
                                     @Param("targetDisplayOrder") int targetDisplayOrder, 
                                     @Param("shiftAmount") int shiftAmount);

    // NEW: Count visible records within a specific display order range
    /**
     * Counts active, visible records within a specific display order range for continuity validation.
     * 
     * @param personId  the unique identifier of the person
     * @param minOrder  the inclusive lower bound of the display order range
     * @param maxOrder  the inclusive upper bound of the display order range
     * @return the count of matching visible records
     */
    @Query("""
        SELECT COUNT(p) 
        FROM PersonWorkExperience p 
        WHERE p.id.personId = :personId 
          AND p.visible = true 
          AND p.displayOrder BETWEEN :minOrder AND :maxOrder
    """)
    long countVisibleBetweenOrders(@Param("personId") Long personId, @Param("minOrder") int minOrder, @Param("maxOrder") int maxOrder);


    /**
     * CASCADING SWAP: Fixes a company typo across all WorkExperiences owned by this specific person.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WorkExperience we 
        SET we.company.id = :newCompanyId 
        WHERE we.company.id = :oldCompanyId 
          AND we.id IN (
              SELECT pwe.id.workExperienceId 
              FROM PersonWorkExperience pwe 
              WHERE pwe.id.personId = :personId
          )
    """)
    int bulkUpdateCompanyIdForPerson(
        @Param("personId") Long personId, 
        @Param("oldCompanyId") Long oldCompanyId, 
        @Param("newCompanyId") Long newCompanyId
    );
}
