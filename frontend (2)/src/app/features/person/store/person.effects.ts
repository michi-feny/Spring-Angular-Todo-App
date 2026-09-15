import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, filter, map, mergeMap, switchMap, withLatestFrom } from 'rxjs/operators';
import * as PersonActions from './person.actions';
import { Store } from '@ngrx/store';
import { FlashMessageState, MessageType } from '../../../store/fleshMessage/fleshMessage.models';
import { addFlashMessage } from '../../../store/fleshMessage/fleshMessage.actions';
import { PersonService } from '../../../core/servises/person/person';
import { PersonData } from '../../../types/dto/person/person-data';
import { selectLoadingDetailIds, selectPersonDetailsCache } from './person.selectors';
import { PersonPhoneNumberService } from '../../../core/servises/person/related/person-phone-number';

@Injectable()
export class PersonEffects {
  private actions$ = inject(Actions);
  private personService = inject(PersonService);
  private personPhoneNumberService = inject(PersonPhoneNumberService);
  private store = inject(Store<FlashMessageState>);

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
        //add filters
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
      withLatestFrom(
        this.store.select(selectPersonDetailsCache),
        this.store.select(selectLoadingDetailIds)
      ),
      filter(([{ id }, cache, loadingIds]) => !cache[id] && !loadingIds.includes(id)),
      map(([{ id }]) => PersonActions.loadPersonDetails({ id }))
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
            console.log(response.data);
            const newPhoneNumber = response.data.value || data;
            return PersonActions.createPhoneNumberSuccess({ data: newPhoneNumber });
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
            console.log(response.data);
            const newPhoneNumber = response.data.value || data;
            return PersonActions.updatePhoneNumberSuccess({ data: newPhoneNumber });
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
}