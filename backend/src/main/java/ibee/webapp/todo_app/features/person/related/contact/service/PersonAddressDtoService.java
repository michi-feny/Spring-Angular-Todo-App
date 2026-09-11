package ibee.webapp.todo_app.features.person.related.contact.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.BusinessWriteDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.MainFlagBusinessRuleService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractPersonRelatedDtoQuerryAndDeleteServiceService;
import ibee.webapp.todo_app.core.service.person.related.contact.address.PersonAddressServiceImpl;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;

@Service
@Transactional
public class PersonAddressDtoService
        extends AbstractPersonRelatedDtoQuerryAndDeleteServiceService //AbstractPersonRelatedDtoService
            <PersonAddressDto, 
            PersonAddress, 
            PersonAddressId, 
            PersonAddressDtoId>
        implements 
        BusinessWriteDtoService
            <PersonAddressDto, PersonAddressDtoId>,
        MainFlagBusinessRuleService<PersonAddressDto, PersonAddress> {


    private final PersonAddressServiceImpl addressEntityService;

    public PersonAddressDtoService(
            PersonAddressServiceImpl personEntityService,
            PersonAddressMapper mapper,
            PersonAddressReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.addressEntityService = personEntityService;
    }


    @Override
    public ServiceResult<PersonAddressDto> create(PersonAddressDto dto) {
        return enforceSingleMainRuleOnCreate(
                mapper.toEntity(dto),
                PersonAddress::isMainAddress,
                e -> e.getId().getPersonId(),
                addressEntityService::findMainAddress,
                personEntityService::create,
                mapper::toDto,
                "PERSON_MAIN_ADDRESS_EXISTS",
                "The person already has a main address."
        );
    }

    

    @Override
    public ServiceResult<PersonAddressDto> update(PersonAddressDto dto, PersonAddressDtoId dtoId) {
        PersonAddress entity = mapper.toEntity(dto);
        PersonAddressId entityId = idReferenceMapper.toEntity(dtoId);

        // For updates, we also need a way to find the current entity by ID.
        // PersonAddressServiceImpl can expose findByIdWithoutException or similar.
        return enforceSingleMainRuleOnUpdate(
                entity,
                entityId,
                PersonAddress::isMainAddress,
                e -> e.getId().getPersonId(),
                // Use explicit lambdas to avoid method reference ambiguity on overloaded methods
                id -> addressEntityService.findByIdWithoutException(id),
                personId -> addressEntityService.findMainAddress(personId),
                (ent, id) -> addressEntityService.update(ent, id),
                mapper::toDto,
                "PERSON_MAIN_ADDRESS_EXISTS",
                "The person already has a main address."
        );
    }

   
    
}
