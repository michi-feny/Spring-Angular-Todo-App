package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.additionalHardSkill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.hardSkills.AdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonAdditionalHardSkillRepository;
import ibee.webapp.todo_app.core.service.AdditionalHardSkillServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonAdditionalHardSkillMapper;

@Service
@Transactional
public class PersonAdditionalHardSkillServiceImpl
        extends PersonRelatedServiceImpl
        <PersonAdditionalHardSkill, 
        PersonAdditionalHardSkillId> {


    private final AdditionalHardSkillServiceImpl dictionaryService;
    private final PersonAdditionalHardSkillRepository repository;

    public PersonAdditionalHardSkillServiceImpl(
            PersonAdditionalHardSkillRepository repository,
            PersonAdditionalHardSkillMapper mapper,
            AdditionalHardSkillServiceImpl dictionaryService) {
        
        super(repository, mapper);
        this.repository = repository;
        this.dictionaryService = dictionaryService;
    }

    @Override
    public PersonAdditionalHardSkill create(PersonAdditionalHardSkill entity) {
        // 1. Resolve Dictionary Child
        AdditionalHardSkill resolvedSkill = dictionaryService.create(entity.getAdditionalHardSkill());

        // 2. Re-attach and Update Composite ID
        entity.setAdditionalHardSkill(resolvedSkill);
        entity.getId().setAdditionalHardSkillId(resolvedSkill.getId());

        return super.create(entity);
    }

    @Override
    public PersonAdditionalHardSkill update(PersonAdditionalHardSkill incoming, PersonAdditionalHardSkillId currentId) {
        // 1. Resolve Dictionary Child
        AdditionalHardSkill resolvedSkill = incoming.getAdditionalHardSkill().getId() != null
                ? dictionaryService.update(incoming.getAdditionalHardSkill(), incoming.getAdditionalHardSkill().getId())
                : dictionaryService.create(incoming.getAdditionalHardSkill());

        incoming.setAdditionalHardSkill(resolvedSkill);

        // 2. Build the correct new ID
        PersonAdditionalHardSkillId newId = new PersonAdditionalHardSkillId(
                currentId.getPersonId(),
                resolvedSkill.getId()
        );
        incoming.setId(newId);

        // 3. ID Swap Logic
        if (!currentId.equals(newId)) {
            repository.deleteById(currentId);
            return super.create(incoming); 
        }

        return super.update(incoming, currentId);
    }

    
}
