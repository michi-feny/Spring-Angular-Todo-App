package ibee.webapp.todo_app.core.service.person;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


import ibee.webapp.todo_app.core.dto.person.PersonData;
import ibee.webapp.todo_app.core.dto.person.PersonOverview;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.mapper.person.PersonMapper;
import ibee.webapp.todo_app.core.service.baseService.newApproach.AbstractCrudDtoService;
// Import der konsistenten Related DTO Services für die ID-Listen im Overview
import ibee.webapp.todo_app.core.service.person.related.contact.address.PersonAddressDtoService;
import ibee.webapp.todo_app.core.service.person.related.contact.country.PersonCountryDtoService;
import ibee.webapp.todo_app.core.service.person.related.contact.mail.PersonEmailAddressDtoService;
import ibee.webapp.todo_app.core.service.person.related.contact.phone.PersonPhoneNumberDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.additionalHardSkill.PersonAdditionalSkillDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.degree.PersonDegreeDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.professionQualification.PersonProfessionQualificationDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.softSkill.PersonSoftSkillDtoService;

@Service
@Transactional
public class PersonDtoService 
extends AbstractCrudDtoService<PersonData, Person, Long>
       {

    private final PersonService personService;
    private final PersonMapper personMapper;

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
            PersonService personEntityService,
            PersonMapper personMapper,
            PersonAddressDtoService personAddressDtoService,
            PersonPhoneNumberDtoService personPhoneNumberDtoService,
            PersonEmailAddressDtoService personEmailAddressDtoService,
            PersonCountryDtoService personCountryDtoService,
            PersonDegreeDtoService personDegreeDtoService,
            PersonProfessionQualificationDtoService personProfessionQualificationDtoService,
            PersonAdditionalSkillDtoService personAdditionalSkillDtoService,
            PersonSoftSkillDtoService personSoftSkillDtoService) {
        
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
        super(personEntityService, personMapper);
    }

    // --- Overview Aggregation (Initialansicht mit Lazy-ID-Listen) ---

    @Transactional(readOnly = true)
    public Optional<PersonOverview> getOverviewById(Long personId) {
        return personService.findById(personId)
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
    public List<PersonData> searchBySocialRecordNumber(Short socialRecordNumber) {
        return personMapper.toDtoList(personService.findBySocialRecordNumber(socialRecordNumber));
    }

    @Transactional(readOnly = true)
    public List<PersonData> searchByFilter(String firstName, String lastName, LocalDate birthDate, Short socialRecordNumber) {
        List<Person> persons = personService.findByFilter(firstName, lastName, birthDate, socialRecordNumber);
        return personMapper.toDtoList(persons);
    }
    

    
}
