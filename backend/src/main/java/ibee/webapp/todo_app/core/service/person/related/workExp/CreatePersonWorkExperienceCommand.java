package ibee.webapp.todo_app.core.service.person.related.workExp;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.service.WorkExperienceServiceImpl;

import java.util.List;

@Component
public class CreatePersonWorkExperienceCommand {

    private final PersonWorkExperienceSequenceHelper sequenceHelper;
    private final WorkExperienceServiceImpl coreWorkExperienceService;
    private final PersonWorkExperienceServiceImpl personWorkExperienceServiceImpl;

    public CreatePersonWorkExperienceCommand(
            PersonWorkExperienceSequenceHelper sequenceHelper,
            WorkExperienceServiceImpl coreWorkExperienceService,
            @Lazy PersonWorkExperienceServiceImpl personWorkExperienceServiceImpl
        ) {
        this.sequenceHelper = sequenceHelper;
        this.coreWorkExperienceService = coreWorkExperienceService;
        this.personWorkExperienceServiceImpl = personWorkExperienceServiceImpl;
    }

    public PersonWorkExperience execute(PersonWorkExperience entity) {
        Long personId = entity.getId().getPersonId();

        // 1. Fetch and Pre-Normalize state safely
        List<PersonWorkExperience> visibleRecords = 
        personWorkExperienceServiceImpl.findVisibleByPersonId(personId);
        if (sequenceHelper.needsNormalization(visibleRecords)) {
            personWorkExperienceServiceImpl.saveAll(sequenceHelper.normalize(visibleRecords));
        }

        // 2. Core Entity Resolution & Timelines
        if (entity.getWorkExperience() != null) {
            var workExp = entity.getWorkExperience();
            personWorkExperienceServiceImpl.validateChronologicalTimeline(workExp);
            
            var resolvedWorkExp = coreWorkExperienceService.create(entity.getWorkExperience());

            entity.setWorkExperience(resolvedWorkExp);
            entity.setId(new PersonWorkExperienceId(personId, resolvedWorkExp.getId()));
        }

        // 3. Perform Shift Math & Save explicitly shifted rows
        // Passing 'null' as oldOrder signals this is a brand-new insertion
        List<PersonWorkExperience> shiftedRecords = sequenceHelper.adjustDisplayOrdersForPlacement(entity, null, visibleRecords);
        if (!shiftedRecords.isEmpty()) {
            personWorkExperienceServiceImpl.saveAll(shiftedRecords);
        }

        return entity;
    }

    
}
