package ibee.webapp.todo_app.features.person.dto;


import java.util.List;

import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
/*
* This is the initial response for the Person screen.
* The important point is:
* PersonOverview does not contain all addresses, 
* phones, degrees, etc.
* Those are loaded when their main accordion section 
* is opened.
 */
import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;

public record PersonOverview(

        PersonData person,

        List<PersonAddressDtoId> addresses,

        List<PersonPhoneNumberDtoId> phones,

        List<PersonEmailAddressDtoId> emails,

        List<PersonCountryDtoId> nationalities,

        List<PersonDegreeDtoId> degrees,

        List<PersonProfessionQualificationDtoId> professions,

        List<PersonAdditionalHardSkillDtoId> additionalSkills,

        List<PersonSoftSkillDtoId> softSkills,

        List<PersonWorkExperienceDtoId> workExp

) {
}
