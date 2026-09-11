package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.professionQualification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.core.entity.hardSkills.ProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonProfessionQualificationRepository;
import ibee.webapp.todo_app.core.service.EducationInstitutionServiceImpl;
import ibee.webapp.todo_app.core.service.ProfessionQualificationServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedServiceImpl;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonProfessionQualificationMapper;

@Service
@Transactional
public class PersonProfessionQualificationServiceImpl
        extends PersonRelatedServiceImpl
        <PersonProfessionQualification, 
        PersonProfessionQualificationId> {


    private final EducationInstitutionServiceImpl educationService;
    private final ProfessionQualificationServiceImpl professionService;
    private final PersonProfessionQualificationRepository repository;

    public PersonProfessionQualificationServiceImpl(
        PersonProfessionQualificationRepository repository,
        PersonProfessionQualificationMapper mapper,
        EducationInstitutionServiceImpl educationService,
        ProfessionQualificationServiceImpl professionService
        ) {
        super(repository, mapper);
        this.repository = repository;
        this.educationService = educationService;
        this.professionService = professionService;
    }

    @Override
    public PersonProfessionQualification create(PersonProfessionQualification entity) {
        // 1. Resolve Children (Deduplicate or Insert)
        ProfessionQualification resolvedProf = professionService.create(entity.getProfessionQualification());
        EducationInstitution resolvedEdu = educationService.create(entity.getEducationInstitution());

        // 2. Re-attach resolved managed entities
        entity.setProfessionQualification(resolvedProf);
        entity.setEducationInstitution(resolvedEdu);

        // 3. Update Composite ID
        entity.getId().setProfessionQualificationId(resolvedProf.getId());
        entity.getId().setEducationInstitutionId(resolvedEdu.getId());

        return super.create(entity);
    }

    @Override
    public PersonProfessionQualification update(PersonProfessionQualification incoming, PersonProfessionQualificationId currentId) {
        // 1. Resolve Children (Use update to fix typos, or create if totally new)
        ProfessionQualification resolvedProf = incoming.getProfessionQualification().getId() != null
                ? professionService.update(incoming.getProfessionQualification(), incoming.getProfessionQualification().getId())
                : professionService.create(incoming.getProfessionQualification());

        EducationInstitution resolvedEdu = incoming.getEducationInstitution().getId() != null
                ? educationService.update(incoming.getEducationInstitution(), incoming.getEducationInstitution().getId())
                : educationService.create(incoming.getEducationInstitution());

        incoming.setProfessionQualification(resolvedProf);
        incoming.setEducationInstitution(resolvedEdu);

        // 2. Build the correct new ID
        PersonProfessionQualificationId newId = new PersonProfessionQualificationId(
                currentId.getPersonId(),
                resolvedProf.getId(),
                resolvedEdu.getId()
        );
        incoming.setId(newId);

        // 3. ID Swap Logic: If the composite ID changed, we must DELETE and INSERT.
        if (!currentId.equals(newId)) {
            repository.deleteById(currentId);
            return super.create(incoming);
        }

        // If IDs are the same, just do a normal update on the link table fields (dates, certificates)
        return super.update(incoming, currentId);
    }


}
