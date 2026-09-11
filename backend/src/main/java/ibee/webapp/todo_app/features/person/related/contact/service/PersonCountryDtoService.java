package ibee.webapp.todo_app.features.person.related.contact.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractPersonRelatedDtoQuerryAndDeleteServiceService;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.BusinessWriteDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.MainFlagBusinessRuleService;
import ibee.webapp.todo_app.core.service.person.related.contact.country.PersonCountryServiceImpl;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonCountryDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.mapper.person.contact.PersonCountryMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonCountryReferenceMapper;

@Service
@Transactional
public class PersonCountryDtoService
        extends AbstractPersonRelatedDtoQuerryAndDeleteServiceService
        <PersonCountryDto, 
        PersonCountry, 
        PersonCountryId, 
        PersonCountryDtoId>
        implements BusinessWriteDtoService
            <PersonCountryDto, PersonCountryDtoId>,
        MainFlagBusinessRuleService
            <PersonCountryDto, PersonCountry> {

    private final PersonCountryServiceImpl entityService;
    private final PersonCountryMapper mapper;

    public PersonCountryDtoService(
            PersonCountryServiceImpl entityService,
            PersonCountryMapper mapper,
            PersonCountryReferenceMapper idReferenceMapper) {
        super(entityService, mapper, idReferenceMapper);
        this.entityService = entityService;
        this.mapper = mapper;
    }

    @Override
    public ServiceResult<PersonCountryDto> create(PersonCountryDto dto) {
        return enforceSingleMainRuleOnCreate(
                mapper.toEntity(dto),
                PersonCountry::isMainCountry,
                e -> e.getId().getPersonId(),
                entityService::findMainCountry,
                entityService::create,
                mapper::toDto,
                "PERSON_MAIN_COUNTRY_EXISTS",
                "The person already has a main country."
        );
    }

    @Override
    public ServiceResult<PersonCountryDto> update(PersonCountryDto dto, PersonCountryDtoId dtoId) {
        PersonCountry entity = mapper.toEntity(dto);
        PersonCountryId entityId = idReferenceMapper.toEntity(dtoId);

        
        return enforceSingleMainRuleOnUpdate(
                entity,
                entityId,
                PersonCountry::isMainCountry,
                e -> e.getId().getPersonId(),
                id -> entityService.findByIdWithoutException(id),
                personId -> entityService.findMainCountry(personId),
                (ent, id) -> entityService.update(ent, id),
                mapper::toDto,
                "PERSON_MAIN_COUNTRY_EXISTS",
                "The person already has a main country."
        );
    }
}
