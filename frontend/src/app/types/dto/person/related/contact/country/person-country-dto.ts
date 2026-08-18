import { CountryDto } from "../../../../country.dto";
import { PersonCountryDtoId } from "../../reference/contact/person-country-dto-id";

export interface PersonCountryDto {
    id?: PersonCountryDtoId;
    country: CountryDto;
    mainCountry: boolean;
}
