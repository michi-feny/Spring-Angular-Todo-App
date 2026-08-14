import { AddressDto } from './address.dto';


export interface PersonAddressDto {

    id?: number;

    address: AddressDto;

    mainAddress: boolean;

}