package ibee.webapp.todo_app.core.service.person.related;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;
import ibee.webapp.todo_app.core.service.baseService.newApproach.BaseCrudServiceImpl;


@Transactional
public abstract class PersonRelatedServiceImpl<
        ENTITY extends PersonRelatedEntity,
        ID>
        extends BaseCrudServiceImpl<ENTITY, ID>
        implements PersonRelatedService<ENTITY, ID> {

   protected final PersonRelatedRepository<ENTITY, ID>
            personRelatedRepository;

    protected PersonRelatedServiceImpl(
            PersonRelatedRepository<ENTITY, ID> repository) {

        super(repository);

        this.personRelatedRepository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ID> findIdsByPersonId(Long personId) {

        return personRelatedRepository
                .findIdsByPersonId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findByPersonId(Long personId) {

        List<ID> ids =
                personRelatedRepository.findIdsByPersonId(personId);

        if (ids.isEmpty()) {
            return List.of();
        }

        return personRelatedRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ENTITY> findWithDetailsById(ID id) {

        return personRelatedRepository
                .findWithDetailsById(id);
    }
}
