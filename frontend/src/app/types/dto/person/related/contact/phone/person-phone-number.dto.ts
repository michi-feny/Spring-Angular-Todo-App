import { PhoneNumberDto } from '../../../../phone-number.dto';
import { PersonPhoneNumberDtoId } from '../../reference/contact/person-phone-number-dto-id';


export interface PersonPhoneNumberDto {

    id?: PersonPhoneNumberDtoId;

    phoneNumber: PhoneNumberDto;

    mainNumber: boolean;

}