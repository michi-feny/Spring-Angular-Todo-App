package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.hardSkills.Degree;
import ibee.webapp.todo_app.core.repository.DegreeRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.skills.hard.DegreeMapper;

@Service
@Transactional
public class DegreeServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<Degree, Long> {

    private final DegreeRepository repository;
    private final DegreeMapper mapper;

    public DegreeServiceImpl(
        DegreeRepository repository, 
        DegreeMapper mapper
    ) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Degree create(Degree requested) {
        // Intercept: If the exact degree already exists, return it instead of creating a duplicate
        Optional<Degree> existing = repository.findByNameAndWeight(requested.getName(), requested.getWeight());
        if (existing.isPresent()) {
            return existing.get();
        }
        
        return super.create(requested);
    }

    @Override
    public Degree update(Degree updates, Long id) {
        // Intercept: If the requested update matches an existing degree (but a different ID), 
        // return the existing match to prevent unique constraint collisions
        Optional<Degree> existingMatch = repository.findByNameAndWeight(updates.getName(), updates.getWeight());
        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }
        
        return super.update(updates, id);
    }
}
