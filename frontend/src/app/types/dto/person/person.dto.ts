import { CountryDto } from '../country.dto';
import { PersonAddressDto } from './related/contact/address/person-address.dto';
import { PersonPhoneNumberDto } from './related/contact/phone/person-phone-number.dto';
import { PersonEmailAddressDto } from './related/contact/mail/person-email-address.dto';
import { PersonDegreeDto } from './related/skill/hard/person-degree.dto';
import { PersonProfessionQualificationDto } from './related/skill/hard/person-profession-qualification.dto';
//import { PersonEducationSkillDto } from './person-education-skill.dto';
//import { PersonEducationSkillDto } from './related/contact/skills/hard/person-education-skill.dto';
import { PersonAdditionalHardSkillDto } from './related/skill/hard/person-additional-hard-skill.dto';


export interface PersonDto {

    id?: number;

    socialRecordNumber: number;

    firstName: string;

    lastName: string;

    birthDate: string;


    nationality?: CountryDto;


    addresses: PersonAddressDto[];

    phones: PersonPhoneNumberDto[];

    emails: PersonEmailAddressDto[];

    degrees: PersonDegreeDto[];

    professions: PersonProfessionQualificationDto[];

    //educationSkills: PersonEducationSkillDto[];

    additionalSkills: PersonAdditionalHardSkillDto[];

    softSKills: SoftSkillDto[];

}