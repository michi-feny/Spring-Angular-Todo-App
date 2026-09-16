package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.SoftSkill;
import ibee.webapp.todo_app.core.repository.SoftSkillRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.skills.soft.SoftSkillMapper;

@Service
@Transactional
public class SoftSkillServiceImpl 
    extends MyCrudBaseEntityFacedeServiceImpl<SoftSkill, Long> {

    private final SoftSkillRepository softSkillRepository;

    public SoftSkillServiceImpl(
      SoftSkillRepository repository,
      SoftSkillMapper mapper
    ) {
        super(repository, mapper);
        this.softSkillRepository = repository;
    }    

    // Assuming your repository has a method to find by name (ignoring case is usually best for tags/skills)
    public Optional<SoftSkill> findByName(String name){
        return softSkillRepository.findByNameIgnoreCase(name);
    }

    /**
     * GLOBAL DEDUPLICATION: Overrides the base create method.
     * Ensures we NEVER insert duplicate skill names into the database.
     */
    @Override
    public SoftSkill create(SoftSkill requestedSkill) {
        assertSkillCanBeCreated(requestedSkill);
        
        Optional<SoftSkill> existing =findByName(
            requestedSkill.getName().trim());

        // The skill already exists somewhere in the database
        if(existing.isPresent()){
            return existing.get();
        }

        // If it doesn't exist, let the base class actually insert it into the DB!
        return super.create(requestedSkill);
    }

    /*
     * ========================================================
     * UPDATE -> DEDUPLICATION
     * ========================================================
     * The Golden Rule for Callers:
     * If someone corrects a typo (e.g., changes "Lidership" to "Leadership"), 
     * but "Leadership" already exists under a DIFFERENT ID, we return the existing one.
     * PersonSoftSkillServiceImpl will capture this and swap the composite key!
     */
    @Override
    public SoftSkill update(SoftSkill sourceUpdates, Long id) {
        assertSkillCanBeCreated(sourceUpdates);
        
        // 1. Check if the newly requested string already exists in the DB
        Optional<SoftSkill> existingMatch = findByName(sourceUpdates.getName().trim());
        
        
        // 2. The string exists, AND it belongs to a different ID.
        // Return it so the upstream CompositeHandler can swap the IDs.
        if (existingMatch.isPresent() 
            && !existingMatch.get().getId().equals(id)) {
            
            return existingMatch.get();
        }

        // 3. SAFE TYPO FIX: If it does not exist anywhere else, apply the patch.
        // This will update the underlying row, fixing the typo for everyone linked to it.
        return super.update(sourceUpdates, id);
    }

    private void assertSkillCanBeCreated(SoftSkill skill) {
        if (skill == null || skill.getName() == null || skill.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("SoftSkill and its name cannot be null or empty.");
        }
    }
}
