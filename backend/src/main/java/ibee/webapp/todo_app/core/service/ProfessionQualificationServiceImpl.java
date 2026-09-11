package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.hardSkills.ProfessionQualification;
import ibee.webapp.todo_app.core.repository.ProfessionQualificationRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.skills.hard.ProfessionQualificationMapper;

@Service
@Transactional
public class ProfessionQualificationServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<ProfessionQualification, Long> {

    private final ProfessionQualificationRepository repository;
    private  final ProfessionQualificationMapper mapper;

    public ProfessionQualificationServiceImpl(
        ProfessionQualificationRepository repository, 
        ProfessionQualificationMapper mapper
    ) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper=mapper;
    }

    @Override
    public ProfessionQualification create(ProfessionQualification requested) {
        Optional<ProfessionQualification> existing = repository.findByNameAndWeight(requested.getName(), requested.getWeight());
        if (existing.isPresent()) return existing.get();
        return super.create(requested);
    }

    @Override
    public ProfessionQualification update(ProfessionQualification updates, Long id) {
        Optional<ProfessionQualification> existingMatch = repository.findByNameAndWeight(updates.getName(), updates.getWeight());
        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }
        return super.update(updates, id);
    }
}
