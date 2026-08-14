package ibee.webapp.todo_app.core.dto.person;


import java.util.List;

import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonAdditionalUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionUiId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillUiId;
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

        List<PersonAddressUiId> addresses,

        List<PersonPhoneUiId> phones,

        List<PersonEmailUiId> emails,

        List<PersonCountryUiId> nationalities,

        List<PersonDegreeUiId> degrees,

        List<PersonProfessionUiId> professions,

        List<PersonAdditionalUiId> additionalSkills,

        List<PersonSoftSkillUiId> softSkills

) {
}
