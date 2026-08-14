package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface AdressRepository 
    extends MyBaseCrudRepo<Address, Long>{
    //List<Address> findByStreetAndCity(String street, String city);

}
