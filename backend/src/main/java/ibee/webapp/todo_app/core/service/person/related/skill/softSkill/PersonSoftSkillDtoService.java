package ibee.webapp.todo_app.core.service.person.related.skill.softSkill;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.core.dto.person.skills.soft.PersonSoftSkillDto;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.mapper.person.references.skill.soft.PersonSoftSkillReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.soft.PersonSoftSkillMapper;

@Service
public class PersonSoftSkillDtoService
        extends AbstractPersonRelatedDtoService<
            PersonSoftSkillDto,
            PersonSoftSkill,
            PersonSoftSkillId,
            PersonSoftSkillDtoId> {

    public PersonSoftSkillDtoService(
            PersonRelatedService<PersonSoftSkill, PersonSoftSkillId> personEntityService,
            PersonSoftSkillMapper mapper,
            PersonSoftSkillReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }
}
