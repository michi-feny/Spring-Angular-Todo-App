package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonDegreeDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonDegreeReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonDegreeMapper;

@Service
public class PersonDegreeDtoService
        extends AbstractPersonRelatedDtoService<
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

