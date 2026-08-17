package ibee.webapp.todo_app.core.service.person.related.contact.country;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private final PersonRelatedService<PersonDegree, PersonDegreeId> personEntityService;
    private final PersonDegreeMapper mapper;
    private final PersonDegreeReferenceMapper idMapper;

    public PersonDegreeDtoService(
            PersonRelatedService<PersonDegree, PersonDegreeId> personEntityService,
            PersonDegreeMapper mapper,
            PersonDegreeReferenceMapper idMapper){
        super(personEntityService, mapper, idMapper);
        this.personEntityService = personEntityService;
        this.mapper = mapper;
        this.idMapper = idMapper;
    }

    // example convenience methods (optional)
   /* *public Optional<PersonDegreeDto> findWithDetailsById(PersonDegreeId id) {
        return personEntityService.findWithDetailsById(id).map(mapper::toDto);
    }

    public List<PersonDegreeDto> findByPersonId(Long personId) {
        return mapper.toDtoList(personEntityService.findByPersonId(personId));
    }

    public List<PersonDegreeDtoId> findIdsByPersonId(Long personId) {
        // if you have an id-ref mapper for PersonDegreeId <-> PersonDegreeDtoId, call it here
        // return idRefMapper.toDtoList(personEntityService.findIdsByPersonId(personId));
        var countryIds =personEntityService.findIdsByPersonId(personId);
        return idMapper.toDtoList(countryIds);
    }
        */ 
}
