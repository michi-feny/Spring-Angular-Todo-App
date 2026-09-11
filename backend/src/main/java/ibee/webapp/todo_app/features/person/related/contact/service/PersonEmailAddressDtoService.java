package ibee.webapp.todo_app.features.person.related.contact.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.BusinessWriteDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.MainFlagBusinessRuleService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractPersonRelatedDtoQuerryAndDeleteServiceService;
import ibee.webapp.todo_app.core.service.person.related.contact.mail.PersonEmailAddressServiceImpl;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonEmailAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonEmailAddressReferenceMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonEmailAddressMapper;

@Service
@Transactional
public class PersonEmailAddressDtoService
        extends AbstractPersonRelatedDtoQuerryAndDeleteServiceService
            <PersonEmailAddressDto, 
            PersonEmailAddress, 
            PersonEmailAddressId, 
            PersonEmailAddressDtoId>
        implements 
        BusinessWriteDtoService
            <PersonEmailAddressDto, PersonEmailAddressDtoId>,
        MainFlagBusinessRuleService<PersonEmailAddressDto, PersonEmailAddress> {

    private final PersonEmailAddressServiceImpl emailEntityService;

    public PersonEmailAddressDtoService(
            PersonEmailAddressServiceImpl personEntityService,
            PersonEmailAddressMapper mapper,
            PersonEmailAddressReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.emailEntityService = personEntityService;
    }

    @Override
    public ServiceResult<PersonEmailAddressDto> create(PersonEmailAddressDto dto) {
        return enforceSingleMainRuleOnCreate(
                mapper.toEntity(dto),
                PersonEmailAddress::isMainEmail,
                e -> e.getId().getPersonId(),
                emailEntityService::findMainEmailAddress,
                emailEntityService::create,
                mapper::toDto,
                "PERSON_MAIN_EMAIL_EXISTS",
                "The person already has a main email address."
        );
    }

    @Override
    public ServiceResult<PersonEmailAddressDto> update(PersonEmailAddressDto dto, PersonEmailAddressDtoId dtoId) {
        PersonEmailAddress entity = mapper.toEntity(dto);
        PersonEmailAddressId entityId = idReferenceMapper.toEntity(dtoId);

        return enforceSingleMainRuleOnUpdate(
                entity,
                entityId,
                PersonEmailAddress::isMainEmail,
                e -> e.getId().getPersonId(),
                id -> emailEntityService.findByIdWithoutException(id),
                personId -> emailEntityService.findMainEmailAddress(personId),
                (ent, id) -> emailEntityService.update(ent, id),
                mapper::toDto,
                "PERSON_MAIN_EMAIL_EXISTS",
                "The person already has a main email address."
        );
    }
}
