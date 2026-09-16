import { createAction, props } from '@ngrx/store';
import { PersonData } from '../../../types/dto/person/person-data';
import { PersonDto } from '../../../types/dto/person/person.dto';
import { PhoneNumberDto } from '../../../types/dto/phone-number.dto';
import { PersonPhoneNumber } from '../../../types/person';
import { PersonPhoneNumberDto } from '../../../types/dto/person/related/contact/phone/person-phone-number.dto';
import { PersonPhoneNumberService } from '../../../core/servises/person/related/person-phone-number';
import { PersonPhoneNumberDtoId } from '../../../types/dto/person/related/reference/contact/person-phone-number-dto-id';

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

// --- Reset Form State ---
export const resetSelectedPerson = createAction('[Person] Reset Selected Person');