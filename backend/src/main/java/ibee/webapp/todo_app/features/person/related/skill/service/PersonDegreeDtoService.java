package ibee.webapp.todo_app.features.person.related.skill.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonDegreeRepository;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractMappedPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree.PersonDegreeServiceImpl;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonDegreeDto;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonDegreeReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonDegreeMapper;

@Service
@Transactional
public class PersonDegreeDtoService
        extends AbstractMappedPersonRelatedDtoService<
            PersonDegreeDto,
            PersonDegree,
            PersonDegreeId,
            PersonDegreeDtoId>  
{

    private final PersonDegreeRepository personDegreeRepository;

    public PersonDegreeDtoService(
            PersonDegreeRepository personDegreeRepository,
            PersonDegreeServiceImpl personEntityService,
            PersonDegreeMapper mapper,
            PersonDegreeReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.personDegreeRepository = personDegreeRepository;
    }
}

