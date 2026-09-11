package ibee.webapp.todo_app.core.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.EmailAddress;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@Repository
public interface EmailAddressRepository 
    extends MyFacadeBaseCrudRepository
        <EmailAddress, Long>{

    Optional<EmailAddress> findByEmailAddress(String email);

    
}
