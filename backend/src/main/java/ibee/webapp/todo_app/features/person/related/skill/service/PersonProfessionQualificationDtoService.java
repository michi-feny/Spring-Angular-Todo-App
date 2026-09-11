package ibee.webapp.todo_app.features.person.related.skill.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.hardSkill.PersonProfessionQualificationRepository;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractMappedPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.professionQualification.PersonProfessionQualificationServiceImpl;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonProfessionQualificationReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonProfessionQualificationMapper;

@Service
@Transactional
public class PersonProfessionQualificationDtoService
        extends AbstractMappedPersonRelatedDtoService<
            PersonProfessionQualificationDto,
            PersonProfessionQualification,
            PersonProfessionQualificationId,
            PersonProfessionQualificationDtoId> 
{

    private final PersonProfessionQualificationRepository personProfessionQualificationRepository;

    public PersonProfessionQualificationDtoService(
            PersonProfessionQualificationRepository personProfessionQualificationRepository,
            PersonProfessionQualificationServiceImpl personEntityService,
            PersonProfessionQualificationMapper mapper,
            PersonProfessionQualificationReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.personProfessionQualificationRepository = personProfessionQualificationRepository;
    }
}
