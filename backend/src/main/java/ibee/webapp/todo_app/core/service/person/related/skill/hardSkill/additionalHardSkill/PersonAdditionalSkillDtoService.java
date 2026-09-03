package ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.additionalHardSkill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.dto.person.contact.phone.PersonPhoneNumberDto;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonAdditionalHardSkillReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonAdditionalHardSkillMapper;

@Service
@Transactional
public class PersonAdditionalSkillDtoService
        extends AbstractPersonRelatedDtoService<
            PersonAdditionalHardSkillDto,
            PersonAdditionalHardSkill,
            PersonAdditionalHardSkillId,
            PersonAdditionalHardSkillDtoId> 
        implements PersonRelatedDtoService<
        PersonAdditionalHardSkillDto,
            PersonAdditionalHardSkill,
            PersonAdditionalHardSkillId,
            PersonAdditionalHardSkillDtoId> {

    public PersonAdditionalSkillDtoService(
            PersonRelatedService<PersonAdditionalHardSkill, PersonAdditionalHardSkillId> personEntityService,
            PersonAdditionalHardSkillMapper mapper,
            PersonAdditionalHardSkillReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }
}
