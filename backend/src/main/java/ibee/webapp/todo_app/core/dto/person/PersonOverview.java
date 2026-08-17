package ibee.webapp.todo_app.core.dto.person;


import java.util.List;

import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillDtoId;
/*
* This is the initial response for the Person screen.
* The important point is:
* PersonOverview does not contain all addresses, 
* phones, degrees, etc.
* Those are loaded when their main accordion section 
* is opened.
 */
public record PersonOverview(

        PersonData person,

        List<PersonAddressDtoId> addresses,

        List<PersonPhoneNumberDtoId> phones,

        List<PersonEmailAddressDtoId> emails,

        List<PersonCountryDtoId> nationalities,

        List<PersonDegreeDtoId> degrees,

        List<PersonProfessionQualificationDtoId> professions,

        List<PersonAdditionalHardSkillDtoId> additionalSkills,

        List<PersonSoftSkillDtoId> softSkills

) {
}
