package ibee.webapp.todo_app.core.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ibee.webapp.todo_app.core.entity.Company;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

public interface CompanyRepository 
    extends MyFacadeBaseCrudRepository<Company, Long>{

    //public Optional<Company> findByName(String companyName);

    //public Optional<Company> findByNameAndLegalForm(String companyName, String legalForm);

    public Optional<Company> findByNameAndLegalFormAndAddress_Country(String name, String legalForm, Country country);

    public Optional<Company> findByNameAndLegalFormAndAddress_CountryId(String name, String legalForm, Long countryId);

    //public Optional<Company> findByNameAndAddress_Country(String name, Country country);

    // 2. SAFE FETCH: Tells Hibernate to fetch the nested Address and Country in the exact same SQL query.
    // This entirely prevents the dreaded LazyInitializationException!
    @EntityGraph(attributePaths = {"address", "address.country"})
    Optional<Company> findWithAssociationsById(Long id);

    /**
     * GARBAGE COLLECTION: Deletes any company that is not linked to a WorkExperience.
     * Returns the number of rows deleted.
     */
    @Modifying
    @Query("DELETE FROM Company c WHERE NOT EXISTS (SELECT 1 FROM WorkExperience w WHERE w.company = c)")
    int deleteOrphanedCompanies();
}
