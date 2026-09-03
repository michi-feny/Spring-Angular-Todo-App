package ibee.webapp.todo_app.core.service.person.related.workExp;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ibee.webapp.todo_app.core.entity.WorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.service.WorkExperienceServiceImpl;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MergePersonWorkExperiencesCommand {

    private final PersonWorkExperienceServiceImpl personWorkExperienceServiceImpl;
    private final PersonWorkExperienceSequenceHelper sequenceHelper;
    private final WorkExperienceServiceImpl coreWorkExperienceService;

    public MergePersonWorkExperiencesCommand(
            @Lazy PersonWorkExperienceServiceImpl personWorkExperienceServiceImpl,
            PersonWorkExperienceSequenceHelper sequenceHelper,
            WorkExperienceServiceImpl coreWorkExperienceService) {
        this.personWorkExperienceServiceImpl = personWorkExperienceServiceImpl;
        this.sequenceHelper = sequenceHelper;
        this.coreWorkExperienceService = coreWorkExperienceService;
    }

    public List<PersonWorkExperience> execute(Long personId, List<Long> workExpIdsToMerge, WorkExperience newMasterDetails) {
        // 1. Pre-emptive Normalization
        List<PersonWorkExperience> visibleRecords = personWorkExperienceServiceImpl.findVisibleByPersonId(personId);
        if (sequenceHelper.needsNormalization(visibleRecords)) {
            personWorkExperienceServiceImpl.saveAll(sequenceHelper.normalize(visibleRecords));
        }

        // 2. Validate Timeline & Sub-Records
        personWorkExperienceServiceImpl.validateChronologicalTimeline(newMasterDetails);
        
        List<PersonWorkExperienceId> subIds = workExpIdsToMerge.stream()
                .map(id -> new PersonWorkExperienceId(personId, id)).toList();
        
        List<PersonWorkExperience> subRecords = personWorkExperienceServiceImpl.findAllById(subIds);
        validateAllRecordsFound(workExpIdsToMerge, subRecords);

        for (PersonWorkExperience record : subRecords) {
            if (record.getWorkExperience() != null) 
                    personWorkExperienceServiceImpl.validateChronologicalTimeline(record.getWorkExperience());
        }

        List<Integer> orders = subRecords.stream().map(PersonWorkExperience::getDisplayOrder).sorted().toList();
        int minOrder = orders.get(0);
        int maxOrder = orders.get(orders.size() - 1);

        // 3. Continuity Guard: Database count ensures no records are left stranded in between
        long recordsInBetween = personWorkExperienceServiceImpl.countVisibleBetweenOrders(personId, minOrder, maxOrder);
        if (recordsInBetween != workExpIdsToMerge.size()) {
            throw new IllegalStateException("Merge failed: There are unselected work experiences trapped between the selected ones.");
        }

        // 4. Create Master Core Entity
        var savedMasterWorkExp = newMasterDetails.getId() == null 
                ? coreWorkExperienceService.create(newMasterDetails) 
                : coreWorkExperienceService.findById(newMasterDetails.getId()).orElseGet(() -> coreWorkExperienceService.create(newMasterDetails));

        // 5. Hide Sub-Records (This naturally clears the slot at minOrder)
        personWorkExperienceServiceImpl.hideAndLinkSubRecords(personId, workExpIdsToMerge, savedMasterWorkExp.getId());

        // 6. Save Master PersonWorkExperience Link
        PersonWorkExperience masterRecord = PersonWorkExperience.builder()
                .id(new PersonWorkExperienceId(personId, savedMasterWorkExp.getId()))
                .workExperience(savedMasterWorkExp)
                .visible(true)
                .displayOrder(minOrder)
                .build();
        personWorkExperienceServiceImpl.create(masterRecord);

        // 7. Post-Merge Normalization (To close the gap left by hidden records)
        List<PersonWorkExperience> finalRecords = 
            personWorkExperienceServiceImpl.findVisibleByPersonId(personId);
        if (sequenceHelper.needsNormalization(finalRecords)) {
            return personWorkExperienceServiceImpl.
                saveAll(sequenceHelper.normalize(finalRecords));
            
        }
        return finalRecords;
    }


    private void validateAllRecordsFound(List<Long> requestedIds, List<PersonWorkExperience> foundRecords) {
        Set<Long> foundIds = foundRecords.stream().map(record -> record.getId().getWorkExperienceId()).collect(Collectors.toSet());
        List<Long> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).toList();
        if (!missingIds.isEmpty()) throw new EntityNotFoundException("Merge failed: Records not found or deleted concurrently: " + missingIds);
    }
}
