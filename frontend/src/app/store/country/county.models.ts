import { CountryDto } from "../../types/dto/common/common.dto";


export interface CountriesState {
    countries: CountryDto[];
    loading: boolean;
    error: any;
}

export const initialCountriesState: CountriesState = {
    countries: [],
    loading: false,
    error: null
}