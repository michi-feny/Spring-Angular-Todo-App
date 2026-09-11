package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.EmailAddress;
import ibee.webapp.todo_app.core.repository.EmailAddressRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.EmailAddressMapper;

@Service
@Transactional
public class EmailAddressServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<EmailAddress, Long> {

    private final EmailAddressRepository emailAddressRepository;

    public EmailAddressServiceImpl(EmailAddressRepository repository, EmailAddressMapper mapper) {
        super(repository, mapper);
        this.emailAddressRepository = repository;
    }

    @Override
    public EmailAddress create(EmailAddress requestedEmail) {
        assertEmailCanBeCreated(requestedEmail);

        Optional<EmailAddress> existing = emailAddressRepository.findByEmailAddress(requestedEmail.getEmailAddress());
        if (existing.isPresent()) {
            return existing.get();
        }

        return super.create(requestedEmail);
    }

    @Override
    public EmailAddress update(EmailAddress sourceUpdates, Long id) {
        assertEmailCanBeCreated(sourceUpdates);

        Optional<EmailAddress> existingMatch = emailAddressRepository.findByEmailAddress(sourceUpdates.getEmailAddress());

        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }

        return super.update(sourceUpdates, id);
    }

    private void assertEmailCanBeCreated(EmailAddress emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException("EmailAddress cannot be null.");
        }
        if (emailAddress.getEmailAddress() == null || emailAddress.getEmailAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("EmailAddress must contain a valid email string.");
        }
    }
}
