package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.hardSkills.AdditionalHardSkill;
import ibee.webapp.todo_app.core.repository.AdditionalHardSkillRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.skills.hard.AdditionalHardSkillMapper;

@Service
@Transactional
public class AdditionalHardSkillServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<AdditionalHardSkill, Long> {

    private final AdditionalHardSkillRepository repository;

    public AdditionalHardSkillServiceImpl(
        AdditionalHardSkillRepository repository, 
        AdditionalHardSkillMapper mapper
    ) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public AdditionalHardSkill create(AdditionalHardSkill requested) {
        Optional<AdditionalHardSkill> existing = repository.findByNameAndLevel(
            requested.getName(), 
            requested.getLevel()
        );
        if (existing.isPresent()) {
            return existing.get();
        }
        return super.create(requested);
    }

    @Override
    public AdditionalHardSkill update(AdditionalHardSkill updates, Long id) {
        Optional<AdditionalHardSkill> existingMatch = repository.findByNameAndLevel(
            updates.getName(), 
            updates.getLevel()
        );
        
        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }
        return super.update(updates, id);
    }
}
