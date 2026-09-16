import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, concatMap, filter, map, mergeMap, switchMap, take, withLatestFrom } from 'rxjs/operators';
import * as PersonActions from './person.actions';
import { Store } from '@ngrx/store';
import { FlashMessageState, MessageType } from '../../../store/fleshMessage/fleshMessage.models';
import { addFlashMessage } from '../../../store/fleshMessage/fleshMessage.actions';
import { PersonService } from '../../../core/servises/person/person';
import { selectLoadingDetailIds, selectPersonDetailsCache } from './person.selectors';
import { PersonPhoneNumberService } from '../../../core/servises/person/related/person-phone-number';
import { PersonEmailAddressService } from '../../../core/servises/person/related/person-email-address';
import { PersonAddressService } from '../../../core/servises/person/related/person-address';
import { PersonCountryService } from '../../../core/servises/person/related/person-country';
import { PersonSoftSkillService } from '../../../core/servises/person/related/person-soft-skill';
import { PersonDegreeService } from '../../../core/servises/person/related/person-degree';
import { PersonProfessionQualificationService } from '../../../core/servises/person/related/person-profession-qualification';
import { PersonAdditionalHardSkillService } from '../../../core/servises/person/related/person-additional-hard-skill';
import { PersonWorkExperienceService } from '../../../core/servises/person/related/work-experience';

@Injectable()
export class PersonEffects {
  private actions$ = inject(Actions);
  private personService = inject(PersonService);
  private personPhoneNumberService = inject(PersonPhoneNumberService);
  private personEmailService = inject(PersonEmailAddressService);
  private personAddressService = inject(PersonAddressService);
  private personCountryService = inject(PersonCountryService);
  private personSoftSkillService = inject(PersonSoftSkillService);
  private personDegreeService = inject(PersonDegreeService);
  private personProfessionService = inject(PersonProfessionQualificationService);
  private personAdditionalSkillService = inject(PersonAdditionalHardSkillService);
  private personWorkExperienceService = inject(PersonWorkExperienceService);
  private store = inject(Store<FlashMessageState>);

  private buildCompositeId(idObj: any): string {
    return Object.values(idObj).filter(val => val !== undefined && val !== null).join('_');
  }

  loadPersonDetails$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.loadPersonDetails),
      mergeMap((action) =>
        this.personService.getPersonDetails(action.id).pipe(
          map((response) => {
            return PersonActions.loadPersonDetailsSuccess({ person: response.data });
          }),
          catchError((error) => {
            this.store.dispatch(addFlashMessage({
              messageType: MessageType.Danger,
              text: 'Person konnte nicht geladen werden.',
              timeout: 8000
            }));
            return of(PersonActions.loadPersonDetailsFailure({ id: action.id, error }));
          })
        )
      )
    )
  );

  loadPersonList$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.loadPersonList),
      switchMap((action) =>
        this.personService.searchByFilter({}).pipe(
          map((response) => {
            const persons = response.data.content ?? [];
            return PersonActions.loadPersonListSuccess({ persons });
          }),
          catchError((error) => {
            return of(PersonActions.loadPersonListFailure({ error }));
          })
        )
      )
    )
  );

  onToggleAccordion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.togglePersonAccordion),
      concatMap((action) =>
        this.store.select(selectPersonDetailsCache).pipe(
          take(1),
          withLatestFrom(this.store.select(selectLoadingDetailIds)),
          map(([cache, loadingIds]) => ({
            id: action.id,
            isCached: !!cache[action.id],
            isLoading: loadingIds.includes(action.id),
          }))
        )
      ),
      filter(({ isCached, isLoading }) => !isCached && !isLoading),
      map(({ id }) => PersonActions.loadPersonDetails({ id }))
    )
  );

  updateGeneralData$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.updateGeneralData),
      switchMap(({ data }) =>
        this.personService.update(data.id!, data).pipe(
          map((response) => {
            const updated = response.data || data;
            return PersonActions.updateGeneralDataSuccess({ data: updated });
          }),
          catchError((error) => {
            this.store.dispatch(addFlashMessage({
              messageType: MessageType.Danger,
              text: 'Allgemeine Daten konnten nicht gespeichert werden.',
              timeout: 8000
            }));
            return of(PersonActions.updateGeneralDataFailure({ error }));
          })
        )
      )
    )
  );

  createPhoneNumber$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.createPhoneNumber),
      switchMap(({ data }) =>
        this.personPhoneNumberService.create(data).pipe(
          map((response) => {
            return PersonActions.createPhoneNumberSuccess({ data: response.data.value || data });
          }),
          catchError((error) => {
            return of(PersonActions.createPhoneNumberFailure({ error }));
          })
        )
      )
    )
  );

  updatePhoneNumber$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.updatePhoneNumber),
      switchMap(({ data }) =>
        this.personPhoneNumberService.update(`${data.id.personId}_${data.id.phoneNumberId}`, data).pipe(
          map((response) => {
            return PersonActions.updatePhoneNumberSuccess({ data: response.data.value || data });
          }),
          catchError((error) => {
            return of(PersonActions.updatePhoneNumberFailure({ error }));
          })
        )
      )
    )
  );

  deletePhoneNumber$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.deletePhoneNumber),
      switchMap(({ id }) =>
        this.personPhoneNumberService.delete(`${id.personId}_${id.phoneNumberId}`).pipe(
          map(() => PersonActions.deletePhoneNumberSuccess({ id })),
          catchError((error) => of(PersonActions.deletePhoneNumberFailure({ error })))
        )
      )
    )
  );

  creatEmail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.createEmail),
      switchMap(({ data }) =>
        this.personEmailService.create(data).pipe(
          map((response) => {
            return PersonActions.createEmailSuccess({ data: response.data.value || data });
          }),
          catchError((error) => {
            return of(PersonActions.createEmailFailure({ error }));
          })
        )
      )
    )
  );

  updateEmail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.updateEmail),
      switchMap(({ data }) =>
        this.personEmailService.update(`${data.id.personId}_${data.id.emailAddressId}`, data).pipe(
          map((response) => {
            return PersonActions.updateEmailSuccess({ data: response.data.value || data });
          }),
          catchError((error) => {
            return of(PersonActions.updateEmailFailure({ error }));
          })
        )
      )
    )
  );

  deleteEmail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.deleteEmail),
      switchMap(({ id }) =>
        this.personEmailService.delete(`${id.personId}_${id.emailAddressId}`).pipe(
          map(() => PersonActions.deleteEmailSuccess({ id })),
          catchError((error) => of(PersonActions.deleteEmailFailure({ error })))
        )
      )
    )
  );

  createAddress$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.createAddress),
      switchMap(({ data }) =>
        this.personAddressService.create(data).pipe(
          map((response) => {
            return PersonActions.createAddressSuccess({ data: response.data.value || data });
          }),
          catchError((error) => of(PersonActions.createAddressFailure({ error })))
        )
      )
    )
  );

  updateAddress$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.updateAddress),
      switchMap(({ data }) =>
        this.personAddressService.update(`${data.id.personId}_${data.id.addressId}`, data).pipe(
          map((response) => {
            return PersonActions.updateAddressSuccess({ data: response.data.value || data });
          }),
          catchError((error) => of(PersonActions.updateAddressFailure({ error })))
        )
      )
    )
  );

  deleteAddress$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.deleteAddress),
      switchMap(({ id }) =>
        this.personAddressService.delete(`${id.personId}_${id.addressId}`).pipe(
          map(() => PersonActions.deleteAddressSuccess({ id })),
          catchError((error) => of(PersonActions.deleteAddressFailure({ error })))
        )
      )
    )
  );

  createNationality$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.createNationality),
      switchMap(({ data }) =>
        this.personCountryService.create(data).pipe(
          map((response) => {
            return PersonActions.createNationalitySuccess({ data: response.data.value || data });
          }),
          catchError((error) => of(PersonActions.createNationalityFailure({ error })))
        )
      )
    )
  );

  updateNationality$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.updateNationality),
      switchMap(({ data }) =>
        this.personCountryService.update(`${data.id.personId}_${data.id.countryId}`, data).pipe(
          map((response) => {
            return PersonActions.updateNationalitySuccess({ data: response.data.value || data });
          }),
          catchError((error) => of(PersonActions.updateNationalityFailure({ error })))
        )
      )
    )
  );

  deleteNationality$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PersonActions.deleteNationality),
      switchMap(({ id }) =>
        this.personCountryService.delete(`${id.personId}_${id.countryId}`).pipe(
          map(() => PersonActions.deleteNationalitySuccess({ id })),
          catchError((error) => of(PersonActions.deleteNationalityFailure({ error })))
        )
      )
    )
  );




  // --- SOFT SKILLS ---
createSoftSkill$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.createSoftSkill),
    switchMap(({ data }) =>
      this.personSoftSkillService.create(data).pipe(
        map((response) => PersonActions.createSoftSkillSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.createSoftSkillFailure({ error })))
      )
    )
  )
);

updateSoftSkill$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.updateSoftSkill),
    switchMap(({ data }) =>
      this.personSoftSkillService.update(`${data.id?.personId}_${data.id?.softSkillId}`, data).pipe(
        map((response) => PersonActions.updateSoftSkillSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.updateSoftSkillFailure({ error })))
      )
    )
  )
);

deleteSoftSkill$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.deleteSoftSkill),
    switchMap(({ id }) =>
      this.personSoftSkillService.delete(`${id.personId}_${id.softSkillId}`).pipe(
        map(() => PersonActions.deleteSoftSkillSuccess({ id })),
        catchError((error) => of(PersonActions.deleteSoftSkillFailure({ error })))
      )
    )
  )
);

// --- DEGREES ---
createDegree$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.createDegree),
    switchMap(({ data }) =>
      this.personDegreeService.create(data).pipe(
        map((response) => PersonActions.createDegreeSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.createDegreeFailure({ error })))
      )
    )
  )
);

updateDegree$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.updateDegree),
    switchMap(({ data }) =>
      this.personDegreeService.update(this.buildCompositeId(data.id), data).pipe(
        map((response) => PersonActions.updateDegreeSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.updateDegreeFailure({ error })))
      )
    )
  )
);

deleteDegree$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.deleteDegree),
    switchMap(({ id }) =>
      this.personDegreeService.delete(this.buildCompositeId(id)).pipe(
        map(() => PersonActions.deleteDegreeSuccess({ id })),
        catchError((error) => of(PersonActions.deleteDegreeFailure({ error })))
      )
    )
  )
);

// --- PROFESSIONS ---
createProfession$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.createProfession),
    switchMap(({ data }) =>
      this.personProfessionService.create(data).pipe(
        map((response) => PersonActions.createProfessionSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.createProfessionFailure({ error })))
      )
    )
  )
);

updateProfession$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.updateProfession),
    switchMap(({ data }) =>
      this.personProfessionService.update(this.buildCompositeId(data.id), data).pipe(
        map((response) => PersonActions.updateProfessionSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.updateProfessionFailure({ error })))
      )
    )
  )
);

deleteProfession$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.deleteProfession),
    switchMap(({ id }) =>
      this.personProfessionService.delete(this.buildCompositeId(id)).pipe(
        map(() => PersonActions.deleteProfessionSuccess({ id })),
        catchError((error) => of(PersonActions.deleteProfessionFailure({ error })))
      )
    )
  )
);

// --- ADDITIONAL SKILLS ---
createAdditionalSkill$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.createAdditionalSkill),
    switchMap(({ data }) =>
      this.personAdditionalSkillService.create(data).pipe(
        map((response) => PersonActions.createAdditionalSkillSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.createAdditionalSkillFailure({ error })))
      )
    )
  )
);

updateAdditionalSkill$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.updateAdditionalSkill),
    switchMap(({ data }) =>
      this.personAdditionalSkillService.update(this.buildCompositeId(data.id), data).pipe(
        map((response) => PersonActions.updateAdditionalSkillSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.updateAdditionalSkillFailure({ error })))
      )
    )
  )
);

deleteAdditionalSkill$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.deleteAdditionalSkill),
    switchMap(({ id }) =>
      this.personAdditionalSkillService.delete(this.buildCompositeId(id)).pipe(
        map(() => PersonActions.deleteAdditionalSkillSuccess({ id })),
        catchError((error) => of(PersonActions.deleteAdditionalSkillFailure({ error })))
      )
    )
  )
);

createWorkExperience$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.createWorkExperience),
    switchMap(({ data }) =>
      this.personWorkExperienceService.create(data).pipe(
        map((response) => PersonActions.createWorkExperienceSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.createWorkExperienceFailure({ error })))
      )
    )
  )
);

updateWorkExperience$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.updateWorkExperience),
    switchMap(({ data }) =>
      this.personWorkExperienceService.update(`${data.id?.personId}_${data.id?.workExperienceId}`, data).pipe(
        map((response) => PersonActions.updateWorkExperienceSuccess({ data: response.data || data })),
        catchError((error) => of(PersonActions.updateWorkExperienceFailure({ error })))
      )
    )
  )
);

deleteWorkExperience$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.deleteWorkExperience),
    switchMap(({ id }) =>
      this.personWorkExperienceService.delete(`${id.personId}_${id.workExperienceId}`).pipe(
        map(() => PersonActions.deleteWorkExperienceSuccess({ id })),
        catchError((error) => of(PersonActions.deleteWorkExperienceFailure({ error })))
      )
    )
  )
);

mergeWorkExperience$ = createEffect(() =>
  this.actions$.pipe(
    ofType(PersonActions.mergeWorkExperience),
    switchMap(({ data }) =>
      this.personWorkExperienceService.merge(data).pipe(
        map((response) => PersonActions.mergeWorkExperienceSuccess({personId: data.personId, workExperiences: response.data.content ?? [] })),
        catchError((error) => of(PersonActions.mergeWorkExperienceFailure({ error })))
      )
    )
  )
);
}