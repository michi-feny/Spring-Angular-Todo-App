import { CountryDto } from './country.dto';


export interface AddressDto {

    id?: number;

    street: string;

    houseNumber: string;

    zipCode: string;

    city: string;

    country?: CountryDto;

}