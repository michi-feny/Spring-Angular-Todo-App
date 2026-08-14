import { PhoneNumberDto } from '../../../phone-number.dto';


export interface PersonPhoneNumberDto {

    id?: number;

    phoneNumber: PhoneNumberDto;

    mainNumber: boolean;

}