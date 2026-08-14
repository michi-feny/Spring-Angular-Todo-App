package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.PhoneNumber;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface PhoneNumberRepository 
    extends MyBaseCrudRepo
        <PhoneNumber, Long>{
}
