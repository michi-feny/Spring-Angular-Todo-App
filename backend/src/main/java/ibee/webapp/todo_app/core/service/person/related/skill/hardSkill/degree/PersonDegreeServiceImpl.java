package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.core.entity.hardSkills.Degree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonDegreeRepository;
import ibee.webapp.todo_app.core.service.DegreeServiceImpl;
import ibee.webapp.todo_app.core.service.EducationInstitutionServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonDegreeMapper;

@Service
@Transactional
public class PersonDegreeServiceImpl
        extends PersonRelatedServiceImpl<PersonDegree, PersonDegreeId> {

    private final EducationInstitutionServiceImpl educationService;
    private final DegreeServiceImpl degreeService;
    private final PersonDegreeRepository repository;

    public PersonDegreeServiceImpl(
        PersonDegreeRepository repository,
        PersonDegreeMapper mapper,
        EducationInstitutionServiceImpl educationService,
        DegreeServiceImpl degreeService
        ) {
        super(repository, mapper);
        this.repository = repository;
        this.educationService = educationService;
        this.degreeService = degreeService;
    }

    @Override
    public PersonDegree create(PersonDegree entity) {
        // 1. Resolve Children (Deduplicate or Insert using your smart dictionary services)
        Degree resolvedDegree = degreeService.create(entity.getDegree());
        EducationInstitution resolvedEdu = educationService.create(entity.getEducationInstitution());

        // 2. Re-attach resolved managed entities
        entity.setDegree(resolvedDegree);
        entity.setEducationInstitution(resolvedEdu);

        // 3. Update Composite ID with the resolved Database IDs
        entity.getId().setDegreeId(resolvedDegree.getId());
        entity.getId().setEducationInstitutionId(resolvedEdu.getId());

        return super.create(entity);
    }

    @Override
    public PersonDegree update(PersonDegree incoming, PersonDegreeId currentId) {
        // 1. Resolve Children (Use update to fix typos, or create if totally new)
        Degree resolvedDegree = incoming.getDegree().getId() != null
                ? degreeService.update(incoming.getDegree(), incoming.getDegree().getId())
                : degreeService.create(incoming.getDegree());

        EducationInstitution resolvedEdu = incoming.getEducationInstitution().getId() != null
                ? educationService.update(incoming.getEducationInstitution(), incoming.getEducationInstitution().getId())
                : educationService.create(incoming.getEducationInstitution());

        incoming.setDegree(resolvedDegree);
        incoming.setEducationInstitution(resolvedEdu);

        // 2. Build the correct new ID
        PersonDegreeId newId = new PersonDegreeId(
                currentId.getPersonId(),
                resolvedDegree.getId(),
                resolvedEdu.getId()
        );
        incoming.setId(newId);

        // 3. ID Swap Logic: If the composite ID changed, we must DELETE and INSERT.
        if (!currentId.equals(newId)) {
            repository.deleteById(currentId);
            return super.create(incoming);
        }

        // If IDs are the same, just do a normal update on the link table fields 
        // (like start date, end date, grades, etc.)
        return super.update(incoming, currentId);
    }
}
