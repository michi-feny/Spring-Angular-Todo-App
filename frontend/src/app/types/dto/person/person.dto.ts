import { CountryDto } from './country.dto';
import { PersonAddressDto } from './person-address.dto';
import { PersonPhoneNumberDto } from './person-phone-number.dto';
import { PersonEmailAddressDto } from './person-email-address.dto';
import { PersonDegreeDto } from './person-degree.dto';
import { PersonProfessionQualificationDto } from './person-profession-qualification.dto';
import { PersonEducationSkillDto } from './person-education-skill.dto';
import { PersonAdditionalHardSkillDto } from './person-additional-hard-skill.dto';


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

    educationSkills: PersonEducationSkillDto[];

    additionalSkills: PersonAdditionalHardSkillDto[];

}