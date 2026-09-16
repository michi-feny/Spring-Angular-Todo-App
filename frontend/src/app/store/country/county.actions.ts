import { createAction, props } from '@ngrx/store';
import { CountryDto } from '../../types/dto/common/common.dto';

export const loadCountries = createAction(
    '[Countries] Load all contries'
);

export const loadCountriesSuccess = createAction(
    '[Countries] Load all contries success',
    props<{ countries: CountryDto[] }>()
);

export const loadCountriesFailure = createAction(
    '[Countries] Load all contries failure',
    props<{ error: any }>()
);