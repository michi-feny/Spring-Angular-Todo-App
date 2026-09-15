package ibee.webapp.todo_app.features.person.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.service.baseService.transport.AbstractMappedCrudDtoService;
import ibee.webapp.todo_app.core.service.person.PersonService;
import ibee.webapp.todo_app.core.service.person.PersonServiceImpl;
import ibee.webapp.todo_app.features.person.dto.PersonData;
import ibee.webapp.todo_app.features.person.dto.PersonDetails;
import ibee.webapp.todo_app.features.person.dto.PersonOverview;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonAddressDtoService;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonCountryDtoService;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonEmailAddressDtoService;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonPhoneNumberDtoService;
import ibee.webapp.todo_app.features.person.related.skill.service.PersonAdditionalSkillDtoService;
import ibee.webapp.todo_app.features.person.related.skill.service.PersonDegreeDtoService;
import ibee.webapp.todo_app.features.person.related.skill.service.PersonProfessionQualificationDtoService;
import ibee.webapp.todo_app.features.person.related.skill.service.PersonSoftSkillDtoService;
import ibee.webapp.todo_app.mapper.person.PersonDetailsMapper;
import ibee.webapp.todo_app.mapper.person.PersonMapper;

@Service
@Transactional
public class PersonDtoService 
extends AbstractMappedCrudDtoService
    <PersonData, Person, Long, Long>
       {

    private final PersonServiceImpl personService;
    private final PersonMapper personMapper;
    private final PersonDetailsMapper personDetailsMapper;

    // Related DTO Services zur Bereitstellung der IDs für das Lazy-Loading im Overview
    private final PersonAddressDtoService personAddressDtoService;
    private final PersonPhoneNumberDtoService personPhoneNumberDtoService;
    private final PersonEmailAddressDtoService personEmailAddressDtoService;
    private final PersonCountryDtoService personCountryDtoService;
    private final PersonDegreeDtoService personDegreeDtoService;
    private final PersonProfessionQualificationDtoService personProfessionQualificationDtoService;
    private final PersonAdditionalSkillDtoService personAdditionalSkillDtoService;
    private final PersonSoftSkillDtoService personSoftSkillDtoService;

    public PersonDtoService(
            PersonServiceImpl personEntityService,
            PersonMapper personMapper,
            PersonAddressDtoService personAddressDtoService,
            PersonPhoneNumberDtoService personPhoneNumberDtoService,
            PersonEmailAddressDtoService personEmailAddressDtoService,
            PersonCountryDtoService personCountryDtoService,
            PersonDegreeDtoService personDegreeDtoService,
            PersonProfessionQualificationDtoService personProfessionQualificationDtoService,
            PersonAdditionalSkillDtoService personAdditionalSkillDtoService,
            PersonSoftSkillDtoService personSoftSkillDtoService,
            PersonDetailsMapper personDetailsMapper) {
        super(personEntityService, personMapper);
        this.personService = personEntityService;
        this.personMapper = personMapper;
        this.personAddressDtoService = personAddressDtoService;
        this.personPhoneNumberDtoService = personPhoneNumberDtoService;
        this.personEmailAddressDtoService = personEmailAddressDtoService;
        this.personCountryDtoService = personCountryDtoService;
        this.personDegreeDtoService = personDegreeDtoService;
        this.personProfessionQualificationDtoService = personProfessionQualificationDtoService;
        this.personAdditionalSkillDtoService = personAdditionalSkillDtoService;
        this.personSoftSkillDtoService = personSoftSkillDtoService;
        this.personDetailsMapper = personDetailsMapper;
    }

    // --- Overview Aggregation (Initialansicht mit Lazy-ID-Listen) ---

    @Transactional(readOnly = true)
    public Optional<PersonOverview> getOverviewById(Long personId) {
        return personService.findByIdWithoutException(personId)
                .map(person -> new PersonOverview(
                        personMapper.toDto(person),
                        personAddressDtoService.findIdsByPersonId(personId),
                        personPhoneNumberDtoService.findIdsByPersonId(personId),
                        personEmailAddressDtoService.findIdsByPersonId(personId),
                        personCountryDtoService.findIdsByPersonId(personId),
                        personDegreeDtoService.findIdsByPersonId(personId),
                        personProfessionQualificationDtoService.findIdsByPersonId(personId),
                        personAdditionalSkillDtoService.findIdsByPersonId(personId),
                        personSoftSkillDtoService.findIdsByPersonId(personId)
                ));
    }

    public Optional<PersonDetails> getDetailsById(Long personId) {
        return personService.findWithDetailsById(personId).map(personDetailsMapper::toDto);
    }
    

    // --- Spezifische Suchfunktionen (DTO-Ebene) ---

    @Transactional(readOnly = true)
    public List<PersonData> searchByFirstName(String firstName) {
        return personMapper.toDtoList(personService.findByFirstName(firstName));
    }

    @Transactional(readOnly = true)
    public List<PersonData> searchByLastName(String lastName) {
        return personMapper.toDtoList(personService.findByLastName(lastName));
    }

    @Transactional(readOnly = true)
    public List<PersonData> searchByBirthDate(LocalDate birthDate) {
        return personMapper.toDtoList(personService.findByBirthDate(birthDate));
    }

    @Transactional(readOnly = true)
    public List<PersonData> searchBySocialRecordNumber(Long socialRecordNumber) {
        return personMapper.toDtoList(personService.findBySocialRecordNumber(socialRecordNumber));
    }

    @Transactional(readOnly = true)
    public List<PersonData> searchByFilter(String firstName, String lastName, LocalDate birthDate, Long socialRecordNumber) {
        List<Person> persons = personService.findByFilter(firstName, lastName, birthDate, socialRecordNumber);
        return personMapper.toDtoList(persons);
    }
    

    
}
