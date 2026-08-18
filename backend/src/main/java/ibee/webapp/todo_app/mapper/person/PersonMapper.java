package ibee.webapp.todo_app.mapper.person;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.Person;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.PersonData;
import ibee.webapp.todo_app.core.dto.person.PersonForm;
import ibee.webapp.todo_app.core.entity.Person;
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
public interface PersonMapper extends BaseMapper<PersonData, Person> {

    // 1. Mappt von der Person-Entity zum PersonData-DTO
    @Override
    PersonData toDto(Person entity);

    // 2. Mappt vom PersonData-DTO zur Person-Entity
    @Override
    Person toEntity(PersonData dto);

    // 3. Optional: Mapping vom Formular-DTO (Create/Update Request) zur Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "nationalitys", ignore = true)
    @Mapping(target = "degrees", ignore = true)
    @Mapping(target = "professions", ignore = true)
    @Mapping(target = "additionalSkills", ignore = true)
    @Mapping(target = "softSkills", ignore = true)
    Person toEntity(PersonForm form);
}
