package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.professionQualification;

import org.springframework.stereotype.Service;

import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonProfessionQualificationReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonProfessionQualificationMapper;

@Service
public class PersonProfessionQualificationDtoService
        extends AbstractPersonRelatedDtoService<
            PersonProfessionQualificationDto,
            PersonProfessionQualification,
            PersonProfessionQualificationId,
            PersonProfessionQualificationDtoId> {

    public PersonProfessionQualificationDtoService(
            PersonRelatedService<PersonProfessionQualification, PersonProfessionQualificationId> personEntityService,
            PersonProfessionQualificationMapper mapper,
            PersonProfessionQualificationReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }
}
