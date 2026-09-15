package ibee.webapp.todo_app.mapper.person;

import org.mapstruct.Mapper;
import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.features.person.dto.PersonDetails;
import org.mapstruct.Mapping;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonAddressMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonCountryMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonEmailAddressMapper;
import ibee.webapp.todo_app.mapper.person.contact.PersonPhoneNumberMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonAdditionalHardSkillMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonDegreeMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonProfessionQualificationMapper;
import ibee.webapp.todo_app.mapper.person.skill.soft.PersonSoftSkillMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        PersonAddressMapper.class,
        PersonCountryMapper.class,
        PersonEmailAddressMapper.class,
        PersonPhoneNumberMapper.class,
        PersonDegreeMapper.class,
        PersonProfessionQualificationMapper.class,
        PersonAdditionalHardSkillMapper.class,
        PersonSoftSkillMapper.class
    }
)
public interface PersonDetailsMapper extends BaseMapper<PersonDetails, Person> {
    @Override
    @Mapping(source = "id", target = "person.id")
    @Mapping(source = "socialRecordNumber", target = "person.socialRecordNumber")
    @Mapping(source = "firstName", target = "person.firstName")
    @Mapping(source = "lastName", target = "person.lastName")
    @Mapping(source = "birthDate", target = "person.birthDate")

    @Mapping(source = "nationalitys", target = "nationalities")
    @Mapping(source = "addresses", target = "addresses")
    @Mapping(source = "phones", target = "phones")
    @Mapping(source = "emails", target = "emails")
    @Mapping(source = "degrees", target = "degrees")
    @Mapping(source = "professions", target = "professions")
    @Mapping(source = "additionalSkills", target = "additionalSkills")
    @Mapping(source = "softSkills", target = "softSkills")
    PersonDetails toDto(Person entity);
}
