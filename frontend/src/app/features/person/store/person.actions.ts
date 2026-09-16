import { createAction, props } from '@ngrx/store';
import { PersonData, PersonDto } from '../../../types/dto/person/person-dto';
import { PersonAddressDto, PersonCountryDto, PersonEmailAddressDto, PersonPhoneNumberDto } from '../../../types/dto/person/person-contact.dto';
import { PersonAdditionalHardSkillDtoId, PersonAddressDtoId, PersonCountryDtoId, PersonDegreeDtoId, PersonEmailAddressDtoId, PersonPhoneNumberDtoId, PersonProfessionQualificationDtoId, PersonSoftSkillDtoId, PersonWorkExperienceDtoId } from '../../../types/dto/person/person-id.dto';
import { MergeWorkExperiencesRequestDto, PersonAdditionalHardSkillDto, PersonDegreeDto, PersonProfessionQualificationDto, PersonSoftSkillDto, PersonWorkExperienceDto } from '../../../types/dto/person/person-skill.dto';
import { WorkExperienceDto } from '../../../types/dto/common/skill.dto';

// --- Load Single Person ---
export const loadPersonList = createAction(
  '[Person] Load Person List'
);

export const loadPersonListSuccess = createAction(
  '[Person] Load Person List Success',
  props<{ persons: PersonData[] }>()
);

export const loadPersonListFailure = createAction(
  '[Person] Load Person List Failure',
  props<{ error: any }>()
);

// --- Load Single Person ---
export const loadPersonDetails = createAction(
  '[Person] Load Person',
  props<{ id: number }>()
);

export const loadPersonDetailsSuccess = createAction(
  '[Person] Load Person Success',
  props<{ person: PersonDto }>()
);

export const loadPersonDetailsFailure = createAction(
  '[Person] Load Person Failure',
  props<{ id: number; error: any }>()
);

export const togglePersonAccordion = createAction(
  '[Person UI] Toggle Accordion',
  props<{ id: number }>()
);

// --- Update Person ---
export const updateGeneralData = createAction(
  '[Person] Update General Data',
  props<{ data: PersonData }>()
  );

export const updateGeneralDataSuccess = createAction(
  '[Person] Update General Data Success',
  props<{ data: PersonData }>()
);

export const updateGeneralDataFailure = createAction(
  '[Person] Update  General Data Failure',
  props<{ error: any }>()
);

// --- Create Phonenumber ---
export const createPhoneNumber = createAction(
  '[Person] Create Phonenumber',
  props<{ data: PersonPhoneNumberDto }>()
);

export const createPhoneNumberSuccess = createAction(
  '[Person] Create Phonenumber Success',
  props<{ data: PersonPhoneNumberDto }>()
);

export const createPhoneNumberFailure = createAction(
  '[Person] Create  Phonenumber Failure',
  props<{ error: any }>()
);

// --- Update Phonenumber ---
export const updatePhoneNumber = createAction(
  '[Person] Update Phonenumber Data',
  props<{ data: PersonPhoneNumberDto }>()
);

export const updatePhoneNumberSuccess = createAction(
  '[Person] Update Phonenumber Success',
  props<{ data: PersonPhoneNumberDto }>()
);

export const updatePhoneNumberFailure = createAction(
'[Person] Update Phonenumber Failure',
props<{ error: any }>()
);

// --- Delete Phonenumber ---
export const deletePhoneNumber = createAction(
  '[Person] Delete Phonenumber',
  props<{ id: PersonPhoneNumberDtoId }>()
);

export const deletePhoneNumberSuccess = createAction(
  '[Person] Delete Phonenumber Success',
  props<{ id: PersonPhoneNumberDtoId }>()
);

export const deletePhoneNumberFailure = createAction(
  '[Person] Delete Phonenumber Failure',
  props<{ error: any }>()
);


// --- Create Email Address ---
export const createEmail = createAction(
  '[Person] Create Email Address',
  props<{ data: PersonEmailAddressDto }>()
);

export const createEmailSuccess = createAction(
  '[Person] CreateEmail Address',
  props<{ data: PersonEmailAddressDto }>()
);

export const createEmailFailure = createAction(
  '[Person] CreateEmail Address Failure',
  props<{ error: any }>()
);

// --- Update Email Address ---
export const updateEmail = createAction(
  '[Person] Update Email Data',
  props<{ data: PersonEmailAddressDto }>()
);

export const updateEmailSuccess = createAction(
  '[Person] Update Email Success',
  props<{ data: PersonEmailAddressDto }>()
);

export const updateEmailFailure = createAction(
  '[Person] Update Email Failure',
  props<{ error: any }>()
);

// --- Delete Email Address ---
export const deleteEmail = createAction(
  '[Person] Delete Email',
  props<{ id: PersonEmailAddressDtoId }>()
);

export const deleteEmailSuccess = createAction(
  '[Person] Delete Email Success',
  props<{ id: PersonEmailAddressDtoId }>()
);

export const deleteEmailFailure = createAction(
  '[Person] Delete Email Failure',
  props<{ error: any }>()
);

// --- Create Address ---
export const createAddress = createAction(
  '[Person] Create Address',
  props<{ data: PersonAddressDto }>()
);

export const createAddressSuccess = createAction(
  '[Person] Create Address Success',
  props<{ data: PersonAddressDto }>()
);

export const createAddressFailure = createAction(
  '[Person] Create Address Failure',
  props<{ error: any }>()
);
// --- Update Address ---
export const updateAddress = createAction(
  '[Person] Update Address Data',
  props<{ data: PersonAddressDto }>()
);

export const updateAddressSuccess = createAction(
  '[Person] Update Address Success',
  props<{ data: PersonAddressDto }>()
);

export const updateAddressFailure = createAction(
  '[Person] Update Address Failure',
  props<{ error: any }>()
);
  // --- Delete Address ---
export const deleteAddress = createAction(
  '[Person] Delete Address',
  props<{ id: PersonAddressDtoId }>()
);

export const deleteAddressSuccess = createAction(
  '[Person] Delete Address Success',
  props<{ id: PersonAddressDtoId }>()
);

export const deleteAddressFailure = createAction(
  '[Person] Delete Address Failure',
  props<{ error: any }>()
);

// --- Create Nationality ---
export const createNationality = createAction(
  '[Person] Create Nationality',
  props<{ data: PersonCountryDto }>()
);
export const createNationalitySuccess = createAction(
  '[Person] Create Nationality Success',
  props<{ data: PersonCountryDto }>()
);

export const createNationalityFailure = createAction(
  '[Person] Create Nationality Failure',
  props<{ error: any }>()
);
// --- Update Nationality ---
export const updateNationality = createAction(
  '[Person] Update Nationality Data',
  props<{ data: PersonCountryDto }>()
);

export const updateNationalitySuccess = createAction(
  '[Person] Update Nationality Success',
  props<{ data: PersonCountryDto }>()
);

export const updateNationalityFailure = createAction(
  '[Person] Update Nationality Failure',
  props<{ error: any }>()
);
// --- Delete Nationality ---
export const deleteNationality = createAction(
  '[Person] Delete Nationality',
  props<{ id: PersonCountryDtoId }>()
);

export const deleteNationalitySuccess = createAction(
  '[Person] Delete Nationality Success',
  props<{ id: PersonCountryDtoId }>()
);

export const deleteNationalityFailure = createAction(
  '[Person] Delete Nationality Failure',
  props<{ error: any }>()
);


// --- Create Soft Skill ---
export const createSoftSkill = createAction(
  '[Person] Create Soft Skill',
  props<{ data: PersonSoftSkillDto }>()
);
export const createSoftSkillSuccess = createAction(
  '[Person] Create Soft Skill Success',
  props<{ data: PersonSoftSkillDto }>()
);
export const createSoftSkillFailure = createAction(
  '[Person] Create Soft Skill Failure',
  props<{ error: any }>()
);

// --- Update Soft Skill ---
export const updateSoftSkill = createAction(
  '[Person] Update Soft Skill',
  props<{ data: PersonSoftSkillDto }>()
);
export const updateSoftSkillSuccess = createAction(
  '[Person] Update Soft Skill Success',
  props<{ data: PersonSoftSkillDto }>()
);
export const updateSoftSkillFailure = createAction(
  '[Person] Update Soft Skill Failure',
  props<{ error: any }>()
);

// --- Delete Soft Skill ---
export const deleteSoftSkill = createAction(
  '[Person] Delete Soft Skill',
  props<{ id: PersonSoftSkillDtoId }>()
);
export const deleteSoftSkillSuccess = createAction(
  '[Person] Delete Soft Skill Success',
  props<{ id: PersonSoftSkillDtoId }>()
);
export const deleteSoftSkillFailure = createAction(
  '[Person] Delete Soft Skill Failure',
  props<{ error: any }>()
);

// --- Create Degree ---
export const createDegree = createAction(
  '[Person] Create Degree',
  props<{ data: PersonDegreeDto }>()
);
export const createDegreeSuccess = createAction(
  '[Person] Create Degree Success',
  props<{ data: PersonDegreeDto }>()
);
export const createDegreeFailure = createAction(
  '[Person] Create Degree Failure',
  props<{ error: any }>()
);

// --- Update Degree ---
export const updateDegree = createAction(
  '[Person] Update Degree',
  props<{ data: PersonDegreeDto }>()
);
export const updateDegreeSuccess = createAction(
  '[Person] Update Degree Success',
  props<{ data: PersonDegreeDto }>()
);
export const updateDegreeFailure = createAction(
  '[Person] Update Degree Failure',
  props<{ error: any }>()
);

// --- Delete Degree ---
export const deleteDegree = createAction(
  '[Person] Delete Degree',
  props<{ id: PersonDegreeDtoId }>()
);
export const deleteDegreeSuccess = createAction(
  '[Person] Delete Degree Success',
  props<{ id: PersonDegreeDtoId }>()
);
export const deleteDegreeFailure = createAction(
  '[Person] Delete Degree Failure',
  props<{ error: any }>()
);

// --- Create Profession ---
export const createProfession = createAction(
  '[Person] Create Profession',
  props<{ data: PersonProfessionQualificationDto }>()
);
export const createProfessionSuccess = createAction(
  '[Person] Create Profession Success',
  props<{ data: PersonProfessionQualificationDto }>()
);
export const createProfessionFailure = createAction(
  '[Person] Create Profession Failure',
  props<{ error: any }>()
);

// --- Update Profession ---
export const updateProfession = createAction(
  '[Person] Update Profession',
  props<{ data: PersonProfessionQualificationDto }>()
);
export const updateProfessionSuccess = createAction(
  '[Person] Update Profession Success',
  props<{ data: PersonProfessionQualificationDto }>()
);
export const updateProfessionFailure = createAction(
  '[Person] Update Profession Failure',
  props<{ error: any }>()
);

// --- Delete Profession ---
export const deleteProfession = createAction(
  '[Person] Delete Profession',
  props<{ id: PersonProfessionQualificationDtoId }>()
);
export const deleteProfessionSuccess = createAction(
  '[Person] Delete Profession Success',
  props<{ id: PersonProfessionQualificationDtoId }>()
);
export const deleteProfessionFailure = createAction(
  '[Person] Delete Profession Failure',
  props<{ error: any }>()
);

// --- Create Additional Skill ---
export const createAdditionalSkill = createAction(
  '[Person] Create Additional Skill',
  props<{ data: PersonAdditionalHardSkillDto }>()
);
export const createAdditionalSkillSuccess = createAction(
  '[Person] Create Additional Skill Success',
  props<{ data: PersonAdditionalHardSkillDto }>()
);
export const createAdditionalSkillFailure = createAction(
  '[Person] Create Additional Skill Failure',
  props<{ error: any }>()
);

// --- Update Additional Skill ---
export const updateAdditionalSkill = createAction(
  '[Person] Update Additional Skill',
  props<{ data: PersonAdditionalHardSkillDto }>()
);
export const updateAdditionalSkillSuccess = createAction(
  '[Person] Update Additional Skill Success',
  props<{ data: PersonAdditionalHardSkillDto }>()
);
export const updateAdditionalSkillFailure = createAction(
  '[Person] Update Additional Skill Failure',
  props<{ error: any }>()
);

// --- Delete Additional Skill ---
export const deleteAdditionalSkill = createAction(
  '[Person] Delete Additional Skill',
  props<{ id: PersonAdditionalHardSkillDtoId }>()
);
export const deleteAdditionalSkillSuccess = createAction(
  '[Person] Delete Additional Skill Success',
  props<{ id: PersonAdditionalHardSkillDtoId }>()
);
export const deleteAdditionalSkillFailure = createAction(
  '[Person] Delete Additional Skill Failure',
  props<{ error: any }>()
);


export const createWorkExperience = createAction(
  '[Person] Create Work Experience',
  props<{ data: PersonWorkExperienceDto }>()
);
export const createWorkExperienceSuccess = createAction(
  '[Person] Create Work Experience Success',
  props<{ data: PersonWorkExperienceDto }>()
);
export const createWorkExperienceFailure = createAction(
  '[Person] Create Work Experience Failure',
  props<{ error: any }>()
);


export const updateWorkExperience = createAction(
  '[Person] Update Work Experience',
  props<{ data: PersonWorkExperienceDto }>()
);
export const updateWorkExperienceSuccess = createAction(
  '[Person] Update Work Experience Success',
  props<{ data: PersonWorkExperienceDto }>()
);
export const updateWorkExperienceFailure = createAction(
  '[Person] Update Work Experience Failure',
  props<{ error: any }>()
);

export const deleteWorkExperience = createAction(
  '[Person] Delete Work Experience',
  props<{ id: PersonWorkExperienceDtoId }>()
);
export const deleteWorkExperienceSuccess = createAction(
  '[Person] Delete Work Experience Success',
  props<{ id: PersonWorkExperienceDtoId }>()
);
export const deleteWorkExperienceFailure = createAction(
  '[Person] Delete Work Experience Failure',
  props<{ error: any }>()
);

export const mergeWorkExperience = createAction(
  '[Person] Merge Work Experiences',
  props<{ data: MergeWorkExperiencesRequestDto }>()
);
export const mergeWorkExperienceSuccess = createAction(
  '[Person] Merge Work Experiences Success',
  props<{ personId: number, workExperiences: PersonWorkExperienceDto[] }>()
);

export const mergeWorkExperienceFailure = createAction(
  '[Person] Merge Work Experiences  Failure',
  props<{ error: any }>()
);
// --- Reset Form State ---
export const resetSelectedPerson = createAction('[Person] Reset Selected Person');