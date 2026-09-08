package ibee.webapp.todo_app.core.service.person.related.workExp;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.service.WorkExperienceServiceImpl;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Component
public class UpdatePersonWorkExperienceCommand {

    private final PersonWorkExperienceServiceImpl personWorkExperienceServiceImpl;
    private final PersonWorkExperienceSequenceHelper sequenceHelper;
    private final WorkExperienceServiceImpl coreWorkExperienceService;

    public UpdatePersonWorkExperienceCommand(
            //PersonWorkExperienceRepository repository,
            PersonWorkExperienceSequenceHelper sequenceHelper,
            WorkExperienceServiceImpl coreWorkExperienceService,
            @Lazy PersonWorkExperienceServiceImpl personWorkExperienceServiceImpl) {
        //this.repository = repository;
        this.sequenceHelper = sequenceHelper;
        this.coreWorkExperienceService = coreWorkExperienceService;
        this.personWorkExperienceServiceImpl = personWorkExperienceServiceImpl;
    }

    public PersonWorkExperience execute(PersonWorkExperience incomingUpdates, PersonWorkExperienceId currentId) {
        Long personId = currentId.getPersonId();

        // 1. Fetch and Pre-Normalize state
        List<PersonWorkExperience> visibleRecords = personWorkExperienceServiceImpl.findVisibleByPersonId(personId);
        if (sequenceHelper.needsNormalization(visibleRecords)) {
            personWorkExperienceServiceImpl.saveAll(sequenceHelper.normalize(visibleRecords));
        }

        PersonWorkExperience existingRecord = personWorkExperienceServiceImpl.findByIdWithoutException(currentId)
                .orElseThrow(() -> new EntityNotFoundException("Record not found for update"));
        Integer oldOrder = existingRecord.getDisplayOrder();

        // 2. Core Entity Resolution & Timelines
        var incomingWorkExp = incomingUpdates.getWorkExperience();
        if (incomingWorkExp != null) {
            personWorkExperienceServiceImpl.validateChronologicalTimeline(incomingWorkExp);
            
            var resolvedWorkExp = incomingWorkExp.getId() != null
                    ? coreWorkExperienceService.findByIdWithoutException(incomingWorkExp.getId())
                        .map(existing -> coreWorkExperienceService.update(incomingWorkExp, existing.getId()))
                        .orElseGet(() -> coreWorkExperienceService.create(incomingWorkExp))
                    : coreWorkExperienceService.create(incomingWorkExp);
            
            incomingUpdates.setWorkExperience(resolvedWorkExp);
            
            if (resolvedWorkExp.getId() != null && !resolvedWorkExp.getId().equals(currentId.getWorkExperienceId())) {
                incomingUpdates.setId(new PersonWorkExperienceId(personId, resolvedWorkExp.getId()));
            }
        }

        // 3. Shift Math & explicitly save shifted rows
        List<PersonWorkExperience> shiftedRecords = sequenceHelper.adjustDisplayOrdersForPlacement(incomingUpdates, oldOrder, visibleRecords);
        if (shiftedRecords.isEmpty() == false) {
            personWorkExperienceServiceImpl.saveAll(shiftedRecords);
        }

        return incomingUpdates;
    }

    
}
