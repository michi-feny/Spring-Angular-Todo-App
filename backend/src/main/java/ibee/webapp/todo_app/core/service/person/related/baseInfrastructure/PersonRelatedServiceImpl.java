package ibee.webapp.todo_app.core.service.person.related.baseInfrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.repository.baseRepo.person.PersonRelatedRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.baseMaper.EntityUpdateMapper;


@Transactional
public abstract class PersonRelatedServiceImpl<
        ENTITY extends PersonRelatedEntity<ID>,
        ID>
        extends MyCrudBaseEntityFacedeServiceImpl<ENTITY, ID>
        implements PersonRelatedService<ENTITY, ID> {

   protected final PersonRelatedRepository<ENTITY, ID>
            personRelatedRepository;

    protected PersonRelatedServiceImpl(
            PersonRelatedRepository<ENTITY, ID> repository,
            EntityUpdateMapper<ENTITY> entityMapper
        ) {

        super(repository, entityMapper);

        this.personRelatedRepository = repository;
    }

    @Override
    protected void validateCompositeId(ID id, String Context) {
        super.validateCompositeId(id, Context);
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

        
        return personRelatedRepository.
            findWithDetailsByPersonId(personId);
      }

    @Override
    @Transactional(readOnly = true)
    public Optional<ENTITY> findByIdWithoutException(ID id) {
        assertData.idNotNull(id);
        // Bridges standard findById lookups to use the eager-fetching details query
        return findWithDetailsByIdWithoutException(id);
    }

    // 1. The pure DB fetch (Returns Optional)
    @Transactional(readOnly = true)
    public Optional<ENTITY> findWithDetailsByIdWithoutException(ID id) {
        assertData.idNotNull(id);
        var entity = personRelatedRepository.findWithDetailsById(id);
        boolean exists = false;
        if(entity.isPresent()){
            exists = true;
            actionEvent.logInfoFoundForFindById(id);
        }else{
            actionEvent.logInfoNotFoundforFindById(id);
        }
            
        //actionEvent.logInfoExists(entity, exists);
        return entity;
    }

    // 2. The business method (Unwraps or throws using YOUR custom builder!)
    @Override
    @Transactional(readOnly = true)
    public ENTITY findWithDetailsById(ID id) {
        return findWithDetailsByIdWithoutException(id).orElseThrow(() ->
            // This reuses your exact i18n translation logic from the base class!
            actionEvent.logWarnAndBuildNotFoundOnFind(id) 
        );
    }
}
