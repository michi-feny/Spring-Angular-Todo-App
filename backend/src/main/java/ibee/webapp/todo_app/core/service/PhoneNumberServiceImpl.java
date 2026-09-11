package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.PhoneNumber;
import ibee.webapp.todo_app.core.repository.PhoneNumberRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.PhoneNumberMapper;

@Service
@Transactional
public class PhoneNumberServiceImpl 
    extends MyCrudBaseEntityFacedeServiceImpl<PhoneNumber, Long>{

    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberServiceImpl(PhoneNumberRepository repository, PhoneNumberMapper mapper) {
        super(repository, mapper);
        this.phoneNumberRepository = repository;
    }    

    Optional<PhoneNumber> findByPhoneNumberAndCountryCodeAndCountryId(
        String phoneNumber, 
        String countryCode, 
        Long countryId
    ) {
        return phoneNumberRepository.findByPhoneNumberAndCountryCodeAndCountryId(
            phoneNumber, countryCode, countryId
        );
    }

    @Override
    public PhoneNumber create(PhoneNumber requestedPhoneNumber) {
        assertPhoneNumberCanBeCreated(requestedPhoneNumber);

        Long countryId = requestedPhoneNumber.getCountry().getId();
        
        Optional<PhoneNumber> existing = phoneNumberRepository.findByPhoneNumberAndCountryCodeAndCountryId(
            requestedPhoneNumber.getPhoneNumber(),
            requestedPhoneNumber.getCountryCode(),
            countryId
        );

        if (existing.isPresent()) {
            return existing.get();
        }

        return super.create(requestedPhoneNumber);
    }

    @Override
    public PhoneNumber update(PhoneNumber sourceUpdates, Long id) {
        assertPhoneNumberCanBeCreated(sourceUpdates);

        Long countryId = sourceUpdates.getCountry().getId();

        Optional<PhoneNumber> existingMatch = phoneNumberRepository.findByPhoneNumberAndCountryCodeAndCountryId(
            sourceUpdates.getPhoneNumber(),
            sourceUpdates.getCountryCode(),
            countryId
        );

        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }

        return super.update(sourceUpdates, id);
    }

    private void assertPhoneNumberCanBeCreated(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("PhoneNumber cannot be null.");
        }

        if (phoneNumber.getPhoneNumber() == null || phoneNumber.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber must contain a valid number string.");
        }

        if (phoneNumber.getCountryCode() == null || phoneNumber.getCountryCode().trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber must contain a valid country code.");
        }

        if (phoneNumber.getCountry() == null || phoneNumber.getCountry().getId() == null) {
            throw new IllegalArgumentException("PhoneNumber must be associated with a valid Country ID.");
        }
    }
}
