import { createFeatureSelector, createSelector } from '@ngrx/store';
import { CountriesState } from './county.models';
import { countriesFeatureKey } from './county.reducer';

export const selectCountriesState = createFeatureSelector<CountriesState>(countriesFeatureKey);

export const selectAllCountries = createSelector(
    selectCountriesState,
    (state: CountriesState) => state.countries
);

export const selectCountriesLoading = createSelector(
    selectCountriesState,
    (state: CountriesState) => state.loading
);

export const selectCountriesError = createSelector(
    selectCountriesState,
    (state: CountriesState) => state.error
);