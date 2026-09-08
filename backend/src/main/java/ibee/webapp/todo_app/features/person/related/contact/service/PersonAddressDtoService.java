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

    @Override
    public PersonAddressDto create(PersonAddressDto dto) {
        // 1. Let the base class save the entity
        var mappedDto = super.create(dto);
        // 2. FOOLPROOF FIX: Forcefully inject the ID from the incoming request
        if (mappedDto.id() == null) {
            return new PersonAddressDto(
                dto.id(), // We know the incoming request has the correct composite ID
                mappedDto.address(), 
                mappedDto.mainAddress()
            );
        }
        return mappedDto;
    }

    @Override
    public PersonAddressDto update(PersonAddressDto dto, PersonAddressId id) {
        var mappedDto = super.update(dto, id);

        // FOOLPROOF FIX for updates
        if (mappedDto.id() == null) {
            return new PersonAddressDto(
                idReferenceMapper.toDto(id), 
                mappedDto.address(), 
                mappedDto.mainAddress()
            );
        }
        return mappedDto;
    }
}
