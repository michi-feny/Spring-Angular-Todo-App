package ibee.webapp.todo_app.features.person.related.contact.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.BusinessWriteDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.MainFlagBusinessRuleService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractPersonRelatedDtoQuerryAndDeleteServiceService;
import ibee.webapp.todo_app.core.service.person.related.contact.phone.PersonPhoneNumberServiceImpl;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonPhoneNumberDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneNumberReferenceMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonPhoneNumberMapper;

@Service
@Transactional
public class PersonPhoneNumberDtoService
        extends AbstractPersonRelatedDtoQuerryAndDeleteServiceService
            <PersonPhoneNumberDto, 
            PersonPhoneNumber, 
            PersonPhoneNumberId, 
            PersonPhoneNumberDtoId>
        implements 
        BusinessWriteDtoService
            <PersonPhoneNumberDto, PersonPhoneNumberDtoId>,
        MainFlagBusinessRuleService<PersonPhoneNumberDto, PersonPhoneNumber> {

    private final PersonPhoneNumberServiceImpl phoneEntityService;

    public PersonPhoneNumberDtoService(
            PersonPhoneNumberServiceImpl personEntityService,
            PersonPhoneNumberMapper mapper,
            PersonPhoneNumberReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.phoneEntityService = personEntityService;
    }

    @Override
    public ServiceResult<PersonPhoneNumberDto> create(PersonPhoneNumberDto dto) {
        return enforceSingleMainRuleOnCreate(
                mapper.toEntity(dto),
                PersonPhoneNumber::isMainPhoneNumber,
                e -> e.getId().getPersonId(),
                phoneEntityService::findMainPhoneNumber,
                phoneEntityService::create,
                mapper::toDto,
                "PERSON_MAIN_PHONE_EXISTS",
                "The person already has a main phone number."
        );
    }

    @Override
    public ServiceResult<PersonPhoneNumberDto> update(PersonPhoneNumberDto dto, PersonPhoneNumberDtoId dtoId) {
        PersonPhoneNumber entity = mapper.toEntity(dto);
        PersonPhoneNumberId entityId = idReferenceMapper.toEntity(dtoId);

        return enforceSingleMainRuleOnUpdate(
                entity,
                entityId,
                PersonPhoneNumber::isMainPhoneNumber,
                e -> e.getId().getPersonId(),
                id -> phoneEntityService.findByIdWithoutException(id),
                personId -> phoneEntityService.findMainPhoneNumber(personId),
                (ent, id) -> phoneEntityService.update(ent, id),
                mapper::toDto,
                "PERSON_MAIN_PHONE_EXISTS",
                "The person already has a main phone number."
        );
    }
}
