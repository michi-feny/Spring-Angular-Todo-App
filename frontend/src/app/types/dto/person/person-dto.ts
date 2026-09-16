import { PersonAddressDto, PersonCountryDto, PersonEmailAddressDto, PersonPhoneNumberDto } from "./person-contact.dto";
import { PersonAdditionalHardSkillDtoId, PersonAddressDtoId, PersonCountryDtoId, PersonDegreeDtoId, PersonEmailAddressDtoId, PersonPhoneNumberDtoId, PersonProfessionQualificationDtoId, PersonSoftSkillDtoId } from "./person-id.dto";
import { PersonAdditionalHardSkillDto, PersonDegreeDto, PersonProfessionQualificationDto, PersonSoftSkillDto } from "./person-skill.dto";

export interface PersonData {
  id?: number | null;
  socialRecordNumber?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  birthDate?: string | null;
}

export interface PersonForm {
  id?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  socialRecordNumber?: string | null;
  birthDate?: string | null;
}

export interface PersonOverview {
  person: PersonData | null;
  addresses: PersonAddressDtoId[];
  phones: PersonPhoneNumberDtoId[];
  emails: PersonEmailAddressDtoId[];
  nationalities: PersonCountryDtoId[];
  degrees: PersonDegreeDtoId[];
  professions: PersonProfessionQualificationDtoId[];
  additionalSkills: PersonAdditionalHardSkillDtoId[];
  softSkills: PersonSoftSkillDtoId[];
}

export interface PersonDto {
  person: PersonData;
  addresses: PersonAddressDto[];
  phones: PersonPhoneNumberDto[];
  emails: PersonEmailAddressDto[];
  nationalities: PersonCountryDto[];
  degrees: PersonDegreeDto[] ;
  professions: PersonProfessionQualificationDto[];
  additionalSkills: PersonAdditionalHardSkillDto[];
  softSkills: PersonSoftSkillDto[];
}