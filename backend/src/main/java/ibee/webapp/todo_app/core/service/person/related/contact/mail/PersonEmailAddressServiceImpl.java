package ibee.webapp.todo_app.core.service.person.related.contact.mail;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ibee.webapp.todo_app.core.entity.EmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.mail.PersonEmailAddressRepository;
import ibee.webapp.todo_app.core.service.EmailAddressServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.core.service.util.CompositeDependentEntityHandler;
import ibee.webapp.todo_app.mapper.person.contact.PersonEmailAddressMapper;

@Service
@Transactional
public class PersonEmailAddressServiceImpl
        extends PersonRelatedServiceImpl<PersonEmailAddress, PersonEmailAddressId> 
        implements CompositeDependentEntityHandler<PersonEmailAddress, PersonEmailAddressId, EmailAddress, Long> {

    @Autowired 
    private EmailAddressServiceImpl emailAddressService;

    private final PersonEmailAddressRepository repository;

    public PersonEmailAddressServiceImpl(PersonEmailAddressRepository repository, PersonEmailAddressMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override public EmailAddress extractChild(PersonEmailAddress parent) { return parent.getEmailAddress(); }
    @Override public void applyChild(PersonEmailAddress parent, EmailAddress child) { parent.setEmailAddress(child); }
    @Override public Long extractChildId(EmailAddress child) { return child.getId(); }
    @Override public Long extractChildIdFromComposite(PersonEmailAddressId parentId) { return parentId.getEmailAddressId(); }
    @Override public PersonEmailAddress instantiateNewParent() { return new PersonEmailAddress(); }
    @Override public PersonEmailAddressId buildNewCompositeId(PersonEmailAddressId oldId, Long newChildId) { 
        return new PersonEmailAddressId(oldId.getPersonId(), newChildId); 
    }
    @Override public void applyCompositeId(PersonEmailAddress parent, PersonEmailAddressId id) { parent.setId(id); }

    @Override
    public PersonEmailAddress create(PersonEmailAddress entity) {
        validateCompositeId(entity.getId(), "PersonEmailAddressId");
        return resolveChildAndPersistNewLink(
                entity,
                emailAddressService,
                (resolvedEntity) -> {
                    resolvedEntity.getId().setEmailAddressId(resolvedEntity.getEmailAddress().getId());
                    return super.create(resolvedEntity);
                }
        );
    }

    @Override
    public PersonEmailAddress update(PersonEmailAddress incomingUpdates, PersonEmailAddressId currentId) {
        validateCompositeId(incomingUpdates.getId(), "Incoming PersonEmailAddressId");
        validateCompositeId(currentId, "Current PersonEmailAddressId");

        return resolveChildAndPersistUpdate(
                incomingUpdates, currentId, emailAddressService,
                this::updateWithMainEmailCleanup, this::createWithMainEmailCleanup,                              
                repository::findById, entityMapper::updateEntityFromEntity, repository::deleteById   
        );
    }

    private void enforceOnlySingleMainEmailRule(PersonEmailAddress entityUpdates) {
        if (entityUpdates.isMainEmail()) {
            repository.resetOtherMainEmailAddresses(entityUpdates.getId().getPersonId(), entityUpdates.getId().getEmailAddressId());
        }
    }

    private PersonEmailAddress updateWithMainEmailCleanup(PersonEmailAddress entity, PersonEmailAddressId id) {
        enforceOnlySingleMainEmailRule(entity);
        return super.update(entity, id);
    }

    private PersonEmailAddress createWithMainEmailCleanup(PersonEmailAddress entity) {
        enforceOnlySingleMainEmailRule(entity);
        return super.create(entity);
    }

    public Optional<PersonEmailAddress> findMainEmailAddress(Long personId) {
        return repository.findByPersonIdAndMainEmailAddressTrue(personId);
    }
}
