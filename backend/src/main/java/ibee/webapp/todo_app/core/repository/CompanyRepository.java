package ibee.webapp.todo_app.core.repository;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.Company;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

public interface CompanyRepository 
    extends MyFacadeBaseCrudRepository<Company, Long>{

    public Optional<Company> findByName(String companyName);

    public Optional<Company> findByNameAndAddress_Country(String name, Country country);
}
