import { AddressDto } from '../../../../address.dto';
import { PersonAddressDtoId } from '../../reference/contact/person-address-dto-id';


export interface PersonAddressDto {

    id?: PersonAddressDtoId;

    address: AddressDto;

    mainAddress: boolean;

}