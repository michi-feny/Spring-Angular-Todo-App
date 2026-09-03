package ibee.webapp.todo_app.features.person.related.country.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.service.person.related.AbstractPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedService;
import ibee.webapp.todo_app.features.person.related.contact.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.country.dto.PersonCountryDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.mapper.person.contact.PersonCountryMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonCountryReferenceMapper;

@Service
@Transactional
public class PersonCountryDtoService
        extends AbstractPersonRelatedDtoService<
            PersonCountryDto,
            PersonCountry,
            PersonCountryId,
            PersonCountryDtoId> 
            implements PersonRelatedDtoService<
            PersonCountryDto,
            PersonCountry,
            PersonCountryId,
            PersonCountryDtoId>{

    private final PersonRelatedService<PersonCountry, PersonCountryId> personEntityService;
    private final PersonCountryMapper mapper;
    private final PersonCountryReferenceMapper idMapper;

    public PersonCountryDtoService(
            PersonRelatedService<PersonCountry, PersonCountryId> personEntityService,
            PersonCountryMapper mapper,
            PersonCountryReferenceMapper idMapper){
        super(personEntityService, mapper, idMapper);
        this.personEntityService = personEntityService;
        this.mapper = mapper;
        this.idMapper = idMapper;
    }

    // example convenience methods (optional)
   /* *public Optional<PersonDegreeDto> findWithDetailsById(PersonDegreeId id) {
        return personEntityService.findWithDetailsById(id).map(mapper::toDto);
    }

    public List<PersonDegreeDto> findByPersonId(Long personId) {
        return mapper.toDtoList(personEntityService.findByPersonId(personId));
    }

    public List<PersonDegreeDtoId> findIdsByPersonId(Long personId) {
        // if you have an id-ref mapper for PersonDegreeId <-> PersonDegreeDtoId, call it here
        // return idRefMapper.toDtoList(personEntityService.findIdsByPersonId(personId));
        var countryIds =personEntityService.findIdsByPersonId(personId);
        return idMapper.toDtoList(countryIds);
    }
        */ 
}
