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
        List<PersonWorkExperience> visibleRecords = personWorkExperienceServiceImpl.findVisibleByPersonId(personId);
        if (sequenceHelper.needsNormalization(visibleRecords)) {
            personWorkExperienceServiceImpl.saveAll(sequenceHelper.normalize(visibleRecords));
        }

        personWorkExperienceServiceImpl.validateChronologicalTimeline(newMasterDetails);

        List<PersonWorkExperienceId> subIds = workExpIdsToMerge.stream()
                .map(id -> new PersonWorkExperienceId(personId, id)).toList();
        
        List<PersonWorkExperience> subRecords = personWorkExperienceServiceImpl.findAllById(subIds);
        validateAllRecordsFound(workExpIdsToMerge, subRecords);

        for (PersonWorkExperience record : subRecords) {
            if (record.getWorkExperience() != null) 
                    personWorkExperienceServiceImpl.validateChronologicalTimeline(record.getWorkExperience());
        }

        List<Integer> sortedOrders = subRecords.stream()
                .map(PersonWorkExperience::getDisplayOrder)
                .sorted()
                .toList();

        for (int i = 0; i < sortedOrders.size() - 1; i++) {
            int currentOrder = sortedOrders.get(i);
            int nextOrder = sortedOrders.get(i + 1);

            if (nextOrder != currentOrder + 1) {
                throw new IllegalStateException("Merge failed: Selected work experiences are not contiguous. Display order was skipped between " + currentOrder + " and " + nextOrder + ".");
            }
        }

        int minOrder = sortedOrders.get(0);
        var savedMasterWorkExp = newMasterDetails.getId() == null
                ? coreWorkExperienceService.create(newMasterDetails)
                : coreWorkExperienceService.findByIdWithoutException(newMasterDetails.getId()).orElseGet(() -> coreWorkExperienceService.create(newMasterDetails));


        PersonWorkExperience masterRecord = PersonWorkExperience.builder()
                .id(new PersonWorkExperienceId(personId, savedMasterWorkExp.getId()))
                .person(subRecords.get(0).getPerson())
                .workExperience(savedMasterWorkExp)
                .visible(true)
                .displayOrder(minOrder)
                .build();

        personWorkExperienceServiceImpl.create(masterRecord);


        personWorkExperienceServiceImpl.hideAndLinkSubRecords(personId, workExpIdsToMerge, savedMasterWorkExp.getId());

        List<PersonWorkExperience> allRecords = personWorkExperienceServiceImpl.findByPersonId(personId);

        List<PersonWorkExperience> activeRecords = allRecords.stream()
                .filter(PersonWorkExperience::isVisible)
                .toList();

        if (sequenceHelper.needsNormalization(activeRecords)) {
            personWorkExperienceServiceImpl.saveAll(sequenceHelper.normalize(activeRecords));
        }   

        return allRecords;
    }


    private void validateAllRecordsFound(List<Long> requestedIds, List<PersonWorkExperience> foundRecords) {
        Set<Long> foundIds = foundRecords.stream().map(record -> record.getId().getWorkExperienceId()).collect(Collectors.toSet());
        List<Long> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).toList();
        if (!missingIds.isEmpty()) throw new EntityNotFoundException("Merge failed: Records not found or deleted concurrently: " + missingIds);
    }
}
