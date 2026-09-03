package ibee.webapp.todo_app.core.service.person.related.workExp;

import java.util.List;
import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.entity.WorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.repository.person.personRelated.workExperience.PersonWorkExperienceRepository;
import ibee.webapp.todo_app.core.service.WorkExperienceServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.workExp.PersonWorkExperienceMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class PersonWorkExperienceServiceImpl 
        extends PersonRelatedServiceImpl<
            PersonWorkExperience, 
            PersonWorkExperienceId> 
        {

    private final PersonWorkExperienceSequenceHelper sequenceHelper;
    private final CreatePersonWorkExperienceCommand createCommand;
    private final UpdatePersonWorkExperienceCommand updateCommand;
    private final MergePersonWorkExperiencesCommand mergeCommand;
    private final WorkExperienceServiceImpl workExperienceServiceImpl;

    private final PersonWorkExperienceRepository repository;

    public PersonWorkExperienceServiceImpl(
            PersonWorkExperienceRepository repository,
            PersonWorkExperienceMapper mapper,
            PersonWorkExperienceSequenceHelper sequenceHelper,
            CreatePersonWorkExperienceCommand createCommand,
            UpdatePersonWorkExperienceCommand updateCommand,
            MergePersonWorkExperiencesCommand mergeCommand,
            WorkExperienceServiceImpl workExperienceServiceImpl) {
        super(repository, mapper);
        this.repository = repository;
        this.sequenceHelper = sequenceHelper;
        this.createCommand = createCommand;
        this.updateCommand = updateCommand;
        this.mergeCommand = mergeCommand;
        this.workExperienceServiceImpl = workExperienceServiceImpl;
    }
   
    public WorkExperienceServiceImpl getWorkExperienceServiceImpl(){
        return workExperienceServiceImpl;
    }


   @Override
    public PersonWorkExperience create(PersonWorkExperience entity) {
       Assert.notNull(entity, "PersonWorkExperience entity cannot be null");
        Assert.notNull(entity.getId(), "PersonWorkExperienceId cannot be null for creation");
        validatePropertyPersonId(entity.getId());

        // Delegate heavy lifting to Command
        PersonWorkExperience preparedEntity = createCommand.execute(entity);
        
        validateCompositeId(preparedEntity.getId(), "PersonWorkExperienceId");
        
        // Save using base generic service logic, followed by final sanity check
        PersonWorkExperience savedEntity = super.create(preparedEntity);
        triggerPostActionNormalization(savedEntity.getId().getPersonId());
        
        return savedEntity;
    }

   

    @Override
    public PersonWorkExperience update(PersonWorkExperience incomingUpdates, PersonWorkExperienceId currentId) {
        Assert.notNull(incomingUpdates, "Update payload cannot be null");
        Assert.notNull(currentId, "Current PersonWorkExperienceId cannot be null");

        PersonWorkExperienceId targetId = incomingUpdates.getId() != null ? incomingUpdates.getId() : currentId;
        validateCompositeId(targetId, "PersonWorkExperienceId");
        validateCompositeId(currentId, "Current PersonWorkExperienceId");

        // Delegate heavy lifting to Command
        PersonWorkExperience preparedEntity = updateCommand.execute(incomingUpdates, currentId);

        // Save using base generic service logic, followed by final sanity check
        PersonWorkExperience updatedEntity = super.update(preparedEntity, currentId);
        triggerPostActionNormalization(currentId.getPersonId());

        return updatedEntity;
    }

    @Override
    public void deleteById(PersonWorkExperienceId id) {
        validateCompositeId(id, "PersonWorkExperienceId");
        Long personId = id.getPersonId();
        
        super.deleteById(id);
        
        // Deleting leaves a gap; automatically close it
        triggerPostActionNormalization(personId);
    }

    @Override
    public void delete(PersonWorkExperience entity) {
        if (entity != null && entity.getId() != null) {
            validateCompositeId(entity.getId(), "PersonWorkExperienceId");
            Long personId = entity.getId().getPersonId();
            
            super.delete(entity);
            triggerPostActionNormalization(personId);
        } else {
            super.delete(entity);
        }
    }

    /**
     * Merges a continuous block of existing work experience entries into a single new master record 
     * within an atomic database transaction. 
     * 
     * <p>This operation enforces strict architectural invariants:
     * <ul>
     *   <li><b>Continuity Guard:</b> Ensures that no unselected active work experiences are trapped 
     *       between the selected rows.</li>
     *   <li><b>Chronological Integrity:</b> Validates that start dates do not succeed end dates 
     *       for both the master details and all participating sub-records.</li>
     *   <li><b>Display Order Compression:</b> Reuses the minimum display order slot of the merged block 
     *       for the new master record and shifts all subsequent follow-up rows left in bulk to close gaps.</li>
     * </ul>
     *
     * @param personId                     the unique identifier of the person owning the work experiences
     * @param workExpIdsToMerge            the list of underlying core work experience IDs targeted for merging
     * @param newMasterWorkExperienceDetails the data payload for the newly minted master work experience entity
     * @throws IllegalArgumentException    if parameters are null or fewer than two records are provided
     * @throws EntityNotFoundException     if any of the specified work experience linkage records cannot be found
     * @throws IllegalStateException       if display orders are missing or if unselected records break continuity
     */
    public List<PersonWorkExperience> mergeWorkExperiences(
            Long personId, 
            List<Long> workExpIdsToMerge, 
            WorkExperience newMasterWorkExperienceDetails
        ) {
        Assert.notNull(personId, "Person ID cannot be null");
        Assert.notNull(workExpIdsToMerge, "Work experience IDs to merge cannot be null");
        Assert.notNull(newMasterWorkExperienceDetails, "New master work experience details cannot be null");
        Assert.isTrue(workExpIdsToMerge.size() > 1, "Must select at least two records to merge");

        // Execute self-contained merge command
        return mergeCommand.execute(personId, workExpIdsToMerge, newMasterWorkExperienceDetails);}

    @Transactional(readOnly = true)
    public List<PersonWorkExperience> findVisibleByPersonId(Long personId) {
        return repository.findVisibleByPersonId(personId);
    }

    @Transactional
    public List<PersonWorkExperience> saveAll(List<PersonWorkExperience> entities) {
        return repository.saveAll(entities);
    }

    @Transactional(readOnly = true)
    public List<PersonWorkExperience> findAllById(Iterable<PersonWorkExperienceId> ids) {
        return repository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public long countVisibleBetweenOrders(Long personId, int minOrder, int maxOrder) {
        return repository.countVisibleBetweenOrders(personId, minOrder, maxOrder);
    }

    @Transactional
    public void hideAndLinkSubRecords(Long personId, List<Long> subIds, Long masterId) {
        repository.hideAndLinkSubRecords(personId, subIds, masterId);
    }


    /**
     * Reusable cleanup hook run at the end of Service-level data mutations
     */
    private void triggerPostActionNormalization(Long personId) {
        List<PersonWorkExperience> visibleRecords = repository.findVisibleByPersonId(personId);
        if (sequenceHelper.needsNormalization(visibleRecords)) {
            repository.saveAll(sequenceHelper.normalize(visibleRecords));
        }
    }

    private void validatePropertyPersonId(PersonWorkExperienceId id) {
        Assert.notNull(id, "Id cannot be null");
        jakartaValidator.validateProperty(id, "personId", "Initial PersonId");
    }

    public void validateChronologicalTimeline(WorkExperience workExperience) {
        if (workExperience.getStartDate() != null && workExperience.getEndDate() != null) {
            Assert.isTrue(!workExperience.getStartDate().isAfter(workExperience.getEndDate()), 
                "Work experience start date cannot be after its end date.");
        }
    }

    
}
