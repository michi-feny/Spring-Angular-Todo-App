import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map, switchMap, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CountryService } from '../../core/servises/country-service';
import * as CountriesActions from './county.actions';

@Injectable()
export class CountriesEffects {
    private actions$ = inject(Actions);
    private countryService = inject(CountryService);

    loadCountries$ = createEffect(() =>
        this.actions$.pipe(
            ofType(CountriesActions.loadCountries),
            switchMap(() =>
                this.countryService.fetchAllCountries().pipe(
                    map((countries) => CountriesActions.loadCountriesSuccess({ countries })),
                    catchError((error) => of(CountriesActions.loadCountriesFailure({ error })))
                )
            )
        )
    );
}