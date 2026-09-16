import { createReducer, on } from '@ngrx/store';
import * as CountriesActions from './county.actions';
import { initialCountriesState } from './county.models';

export const countriesFeatureKey = 'countries';

export const coutriesReducer = createReducer(
    initialCountriesState,

    on(CountriesActions.loadCountries, (state) => ({
        ...state,
        loading: true,
        error: null
    })),
    on(CountriesActions.loadCountriesSuccess, (state, { countries }) => ({
        ...state,
        countries,
        loading: false
    })),
    on(CountriesActions.loadCountriesFailure, (state, { error }) => ({
        ...state,
        loading: false,
        error
    }))
);