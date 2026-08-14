import { AddressDto } from './address.dto';
import { CountryDto } from './country.dto';


export interface EducationInstitutionDto {

    id?: number;

    name: string;

    address?: AddressDto;

   // country?: CountryDto;

}