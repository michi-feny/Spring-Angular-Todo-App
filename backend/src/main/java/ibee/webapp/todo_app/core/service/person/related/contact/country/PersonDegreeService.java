package ibee.webapp.todo_app.core.service.person.related.contact.country;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.repository.person.personRelated.contact.country.PersonCountryRepository;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonDegreeRepository;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.skill.PersonDegreeMapper;

@Service
@Transactional
public class PersonDegreeService
        extends PersonRelatedServiceImpl<
                PersonDegree,
                PersonDegreeDtoId> {

    private final PersonDegreeMapper mapper;

    public PersonDegreeService(
            PersonDegreeRepository repository,
            PersonDegreeMapper mapper) {

        super(repository);

        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PersonDegreeInfo> findInfoByPersonId(
            Long personId) {

        return findByPersonId(personId)
                .stream()
                .map(mapper::toInfo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PersonDegreeInfo> findInfoById(
            PersonDegreeDtoId id) {

        return findWithDetailsById(id)
                .map(mapper::toInfo);
    }

    public PersonDegree save(
            PersonDegreeForm form) {

        return save(mapper.toEntity(form));
    }

    public PersonDegree update(
            PersonDegreeForm form) {

        return save(mapper.toEntity(form));
    }
}
