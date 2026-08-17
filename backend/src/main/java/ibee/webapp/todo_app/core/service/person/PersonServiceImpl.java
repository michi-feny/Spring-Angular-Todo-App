package ibee.webapp.todo_app.core.service.person;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.dto.person.PersonData;
import ibee.webapp.todo_app.core.dto.person.PersonOverview;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.repository.person.PersonRepository;
import ibee.webapp.todo_app.core.service.baseService.newApproach.BaseCrudServiceImpl;
import ibee.webapp.todo_app.core.service.person.related.contact.address.PersonAddressService;
import ibee.webapp.todo_app.mapper.person.PersonMapper;

@Service
@Transactional
public class PersonServiceImpl
        extends BaseCrudServiceImpl<Person, Long>
        implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    private final PersonAddressService addressService;
    private final PersonPhoneService phoneService;
    private final PersonPhoneNumberDtoService emailService;
    private final PersonCountryService countryService;
    private final PersonDegreeServiceImpl degreeService;
    private final PersonProfessionService professionService;
    private final PersonAdditionalSkillService additionalSkillService;
    private final PersonSoftSkillService softSkillService;

    public PersonServiceImpl(
            PersonRepository personRepository,
            PersonMapper personMapper,

            PersonAddressService addressService,
            PersonPhoneService phoneService,
            PersonPhoneNumberDtoService emailService,
            PersonCountryService countryService,
            PersonDegreeServiceImpl degreeService,
            PersonProfessionService professionService,
            PersonAdditionalSkillService additionalSkillService,
            PersonSoftSkillService softSkillService) {

        super(personRepository);

        this.personRepository = personRepository;
        this.personMapper = personMapper;

        this.addressService = addressService;
        this.phoneService = phoneService;
        this.emailService = emailService;
        this.countryService = countryService;
        this.degreeService = degreeService;
        this.professionService = professionService;
        this.additionalSkillService = additionalSkillService;
        this.softSkillService = softSkillService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonData> findDataById(Long id) {

        return personRepository
                .findById(id)
                .map(personMapper::toData);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findWithDetailsById(Long id) {

        return personRepository.findWithDetailsById(id);
    }

    @Override
    public Person save(PersonForm form) {

        Person person =
                personMapper.toEntity(form);

        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonOverview> findOverviewById(
            Long personId) {

        return personRepository
                .findById(personId)
                .map(person -> new PersonOverview(

                        personMapper.toData(person),

                        addressService
                                .findIdsByPersonId(personId),

                        phoneService
                                .findIdsByPersonId(personId),

                        emailService
                                .findIdsByPersonId(personId),

                        countryService
                                .findIdsByPersonId(personId),

                        degreeService
                                .findIdsByPersonId(personId),

                        professionService
                                .findIdsByPersonId(personId),

                        additionalSkillService
                                .findIdsByPersonId(personId),

                        softSkillService
                                .findIdsByPersonId(personId)
                ));
    }
}
