import { PersonData } from "./person-data";
import { PersonAddressDtoId } from "./related/reference/contact/person-address-dto-id";
import { PersonCountryDtoId } from "./related/reference/contact/person-country-dto-id";
import { PersonEmailAddressDtoId } from "./related/reference/contact/person-email-address-dto-id";
import { PersonPhoneNumberDtoId } from "./related/reference/contact/person-phone-number-dto-id";
import { PersonAdditionalHardSkillDtoId } from "./related/reference/skill/person-additional-hard-skill-dto-id";
import { PersonDegreeDtoId } from "./related/reference/skill/person-degree-dto-id";
import { PersonProfessionQualificationDtoId } from "./related/reference/skill/person-profession-qualification-dto-id";
import { PersonSoftSkillDtoId } from "./related/reference/skill/person-soft-skill-dto-id";

export interface PersonOverview {
    person: PersonData;

    addresses: PersonAddressDtoId[];

    phones: PersonPhoneNumberDtoId[];

    emails: PersonEmailAddressDtoId[];

    nationalities: PersonCountryDtoId[];

    degrees: PersonDegreeDtoId[];

    professions: PersonProfessionQualificationDtoId[];

    additionalSkills: PersonAdditionalHardSkillDtoId[];

    softSkills: PersonSoftSkillDtoId[];
}
