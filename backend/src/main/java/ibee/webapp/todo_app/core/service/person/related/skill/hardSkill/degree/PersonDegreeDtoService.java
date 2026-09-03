package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonDegreeDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonDegreeReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonDegreeMapper;

@Service
@Transactional
public class PersonDegreeDtoService
        extends AbstractPersonRelatedDtoService<
            PersonDegreeDto,
            PersonDegree,
            PersonDegreeId,
            PersonDegreeDtoId> 
        implements PersonRelatedDtoService<
            PersonDegreeDto,
            PersonDegree,
            PersonDegreeId,
            PersonDegreeDtoId> {

    public PersonDegreeDtoService(
            PersonRelatedService<PersonDegree, PersonDegreeId> personEntityService,
            PersonDegreeMapper mapper,
            PersonDegreeReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }
}

