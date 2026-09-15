package ibee.webapp.todo_app.core.repository.baseRepo.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@NoRepositoryBean
public interface PersonRelatedRepository
    <ENTITY extends PersonRelatedEntity, ID>
        extends MyFacadeBaseCrudRepository<ENTITY, ID> {


    @Query("""
        SELECT e.id
        FROM #{#entityName} e
        WHERE e.person.id = :personId
    """)
    List<ID> findIdsByPersonId(
        @Param("personId") Long personId
    );

    Optional<ENTITY> findWithDetailsById(ID id);


    // 2. Collection Lookup (Contract for complex Graph fetching)
    List<ENTITY> findWithDetailsByPersonId(Long personId);


    // 4. Utility: Check if a person has any of these records assigned
    boolean existsByPersonId(Long personId);

    // 5. Utility: Count how many of these records a person has
    long countByPersonId(Long personId);

    // 6. Utility: Bulk delete all records of this type for a specific person
    void deleteByPersonId(Long personId);
}
