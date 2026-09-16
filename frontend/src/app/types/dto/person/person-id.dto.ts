export interface PersonAddressDtoId {
  personId: number;
  addressId?: number;
}

export interface PersonCountryDtoId {
  personId: number;
  countryId?: number;
}

export interface PersonEmailAddressDtoId {
  personId: number;
  emailAddressId?: number;
}

export interface PersonPhoneNumberDtoId {
  personId: number;
  phoneNumberId?: number;
}

export interface PersonAdditionalHardSkillDtoId {
  personId: number;
  additionalHardSkillId?: number;
}

export interface PersonDegreeDtoId {
  personId: number;
  degreeId?: number;
  educationInstitutionId?: number;
}


export interface PersonProfessionQualificationDtoId {
  personId: number;
  professionQualificationId?: number;
  educationInstitutionId?: number;
}

export interface PersonSoftSkillDtoId {
  personId: number;
  softSkillId?: number;
}

export interface PersonWorkExperienceDtoId {
  personId: number;
  workExperienceId?: number;
}