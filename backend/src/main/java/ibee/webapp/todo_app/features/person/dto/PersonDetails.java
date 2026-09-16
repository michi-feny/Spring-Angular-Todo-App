package ibee.webapp.todo_app.features.person.dto;
import java.util.List;

import ibee.webapp.todo_app.features.person.related.contact.dto.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonCountryDto;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonEmailAddressDto;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonPhoneNumberDto;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonDegreeDto;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.features.person.related.skill.dto.soft.PersonSoftSkillDto;

public record PersonDetails(
        PersonData person,
        List<PersonAddressDto> addresses,
        List<PersonPhoneNumberDto> phones,
        List<PersonEmailAddressDto> emails,
        List<PersonCountryDto> nationalities,
        List<PersonDegreeDto> degrees,
        List<PersonProfessionQualificationDto> professions,
        List<PersonAdditionalHardSkillDto> additionalSkills,
        List<PersonSoftSkillDto> softSkills
) {
}

