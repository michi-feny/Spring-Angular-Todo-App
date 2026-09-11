package ibee.webapp.todo_app.core.service.person.related.skill.softSkill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ibee.webapp.todo_app.core.entity.SoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.service.SoftSkillServiceImpl;

import ibee.webapp.todo_app.core.repository.person.personRelated.skill.softSkill.PersonSoftSkillRepository;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.core.service.util.CompositeDependentEntityHandler;
import ibee.webapp.todo_app.mapper.person.skill.soft.PersonSoftSkillMapper;

@Service
@Transactional
public class PersonSoftSkillServiceImpl
        extends PersonRelatedServiceImpl
            <PersonSoftSkill, PersonSoftSkillId> 
        implements CompositeDependentEntityHandler
            <PersonSoftSkill, PersonSoftSkillId, SoftSkill, Long> 
        {

    private final SoftSkillServiceImpl softSkillService;
    private final PersonSoftSkillRepository repository;

    public PersonSoftSkillServiceImpl(
        
        PersonSoftSkillRepository repository,
        SoftSkillServiceImpl softSkillService,
        PersonSoftSkillMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.softSkillService = softSkillService;
    }
    @Override public SoftSkill extractChild(PersonSoftSkill parent) { return parent.getSoftSkill(); }
    @Override public void applyChild(PersonSoftSkill parent, SoftSkill child) { parent.setSoftSkill(child); }
    @Override public Long extractChildId(SoftSkill child) { return child.getId(); }
    @Override public Long extractChildIdFromComposite(PersonSoftSkillId parentId) { return parentId.getSoftSkillId(); }
    @Override public PersonSoftSkill instantiateNewParent() { return new PersonSoftSkill(); }
    @Override public PersonSoftSkillId buildNewCompositeId(PersonSoftSkillId oldId, Long newId) { return new PersonSoftSkillId(oldId.getPersonId(), newId); }
    @Override public void applyCompositeId(PersonSoftSkill parent, PersonSoftSkillId id) { parent.setId(id); }

    @Override
    public PersonSoftSkill create(PersonSoftSkill entity) {
        assertData.entityNotNull(entity);
        validateCompositeId(entity.getId(), "PersonSoftSkillId");

        return resolveChildAndPersistNewLink(entity, softSkillService, (resolved) -> {
            resolved.getId().setSoftSkillId(resolved.getSoftSkill().getId());
            return super.create(resolved);
        });
    }

    @Override
    public PersonSoftSkill update(PersonSoftSkill incomingUpdates, PersonSoftSkillId currentId) {
        Assert.notNull(incomingUpdates, "Update payload cannot be null");
        return resolveChildAndPersistUpdate(
                incomingUpdates, currentId, softSkillService,
                super::update, super::create, repository::findById,                     
                entityMapper::updateEntityFromEntity, repository::deleteById                    
        );
    }


}
