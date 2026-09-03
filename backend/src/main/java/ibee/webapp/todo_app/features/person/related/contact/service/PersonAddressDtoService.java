package ibee.webapp.todo_app.features.person.related.contact.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.dto.person.skills.soft.PersonSoftSkillDto;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.features.person.related.contact.PersonAddressDto;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;

@Service
@Transactional
public class PersonAddressDtoService
        extends AbstractPersonRelatedDtoService<
            PersonAddressDto,
            PersonAddress,
            PersonAddressId,
            PersonAddressDtoId> 
        implements PersonRelatedDtoService<
            PersonAddressDto,
            PersonAddress,
            PersonAddressId,
            PersonAddressDtoId>{

    public PersonAddressDtoService(
            PersonRelatedService<PersonAddress, PersonAddressId> personEntityService,
            PersonAddressMapper mapper,
            PersonAddressReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }
}
