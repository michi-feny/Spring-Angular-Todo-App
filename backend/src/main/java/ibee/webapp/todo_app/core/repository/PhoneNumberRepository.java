package ibee.webapp.todo_app.core.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.PhoneNumber;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@Repository
public interface PhoneNumberRepository 
    extends MyFacadeBaseCrudRepository
        <PhoneNumber, Long>{

    Optional<PhoneNumber> findByPhoneNumberAndCountryCodeAndCountryId(
        String phoneNumber, 
        String countryCode, 
        Long countryId
    );
}
