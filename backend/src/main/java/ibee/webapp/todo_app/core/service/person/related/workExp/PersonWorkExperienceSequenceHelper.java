package ibee.webapp.todo_app.core.service.person.related.workExp;

import org.springframework.stereotype.Component;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonWorkExperienceSequenceHelper {

    /**
     * Fast pre-check to see if the sequence is fragmented.
     */
    public boolean needsNormalization(List<PersonWorkExperience> visibleRecords) {
        for (int i = 0; i < visibleRecords.size(); i++) {
            if (visibleRecords.get(i).getDisplayOrder() == null || visibleRecords.get(i).getDisplayOrder() != i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mutates the list in-memory to a perfect 0..N sequence and returns ONLY the modified records.
     */
    public List<PersonWorkExperience> normalize(List<PersonWorkExperience> visibleRecords) {
        List<PersonWorkExperience> modifiedRecords = new ArrayList<>();
        for (int i = 0; i < visibleRecords.size(); i++) {
            PersonWorkExperience record = visibleRecords.get(i);
            if (record.getDisplayOrder() == null || record.getDisplayOrder() != i) {
                record.setDisplayOrder(i);
                modifiedRecords.add(record);
            }
        }
        return modifiedRecords; // Safe to pass directly to repository.saveAll()
    }

    /**
     * Calculates the in-memory shift required for an insertion or update.
     * Returns the list of strictly affected records that need to be persisted.
     */
    public List<PersonWorkExperience> adjustDisplayOrdersForPlacement(
            PersonWorkExperience entityToSave, 
            Integer oldOrder, 
            List<PersonWorkExperience> visibleRecords) {
        
        List<PersonWorkExperience> modifiedRecords = new ArrayList<>();
        Integer newOrder = entityToSave.getDisplayOrder();

        // If no order provided, append to the end
        if (newOrder == null) {
            newOrder = (oldOrder != null) ? oldOrder : visibleRecords.size();
            entityToSave.setDisplayOrder(newOrder);
            return modifiedRecords;
        }

        // No shift needed if position is unchanged
        if (newOrder.equals(oldOrder)) {
            return modifiedRecords; 
        }

        int effectiveOldOrder = (oldOrder != null) ? oldOrder : visibleRecords.size();

        if (newOrder < effectiveOldOrder) {
            // Moving UP. Shift down (+1). Loop backwards to prevent slot collisions.
            for (int i = visibleRecords.size() - 1; i >= 0; i--) {
                PersonWorkExperience rec = visibleRecords.get(i);
                Integer currentOrder = rec.getDisplayOrder();
                if (currentOrder != null && currentOrder >= newOrder && currentOrder < effectiveOldOrder) {
                    rec.setDisplayOrder(currentOrder + 1);
                    modifiedRecords.add(rec);
                }
            }
        } else {
            // Moving DOWN. Shift up (-1). Loop forwards to prevent slot collisions.
            for (int i = 0; i < visibleRecords.size(); i++) {
                PersonWorkExperience rec = visibleRecords.get(i);
                Integer currentOrder = rec.getDisplayOrder();
                if (currentOrder != null && currentOrder > effectiveOldOrder && currentOrder <= newOrder) {
                    rec.setDisplayOrder(currentOrder - 1);
                    modifiedRecords.add(rec);
                }
            }
        }
        return modifiedRecords;
    }
}
