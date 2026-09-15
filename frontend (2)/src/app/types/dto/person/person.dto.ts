import { CountryDto } from '../country.dto';
import { PersonAddressDto } from './related/contact/address/person-address.dto';
import { PersonPhoneNumberDto } from './related/contact/phone/person-phone-number.dto';
import { PersonEmailAddressDto } from './related/contact/mail/person-email-address.dto';
import { PersonDegreeDto } from './related/skill/hard/person-degree.dto';
import { PersonProfessionQualificationDto } from './related/skill/hard/person-profession-qualification.dto';
//import { PersonEducationSkillDto } from './person-education-skill.dto';
//import { PersonEducationSkillDto } from './related/contact/skills/hard/person-education-skill.dto';
import { PersonAdditionalHardSkillDto } from './related/skill/hard/person-additional-hard-skill.dto';
import { PersonSoftSkillDto } from './related/skill/soft/person-soft-skill-dto';
import { PersonCountryDto } from './related/contact/country/person-country-dto';
import { PersonData } from './person-data';


export interface PersonDto {

    person: PersonData

    addresses: PersonAddressDto[];

    emails: PersonEmailAddressDto[];

    phones: PersonPhoneNumberDto[];

    nationalities?: PersonCountryDto[];

    degrees: PersonDegreeDto[];

    professions: PersonProfessionQualificationDto[];

    additionalSkills: PersonAdditionalHardSkillDto[];

    softSkills: PersonSoftSkillDto[];
}