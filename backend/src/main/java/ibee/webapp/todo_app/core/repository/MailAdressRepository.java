package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.EmailAddress;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface MailAdressRepository 
    extends MyBaseCrudRepo
        <EmailAddress, Long>{

}
