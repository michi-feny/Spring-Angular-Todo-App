import { EmailAddressDto } from '../../../../email-address.dto';
import { PersonEmailAddressDtoId } from '../../reference/contact/person-email-address-dto-id';
import { PersonPhoneNumberDtoId } from '../../reference/contact/person-phone-number-dto-id';


export interface PersonEmailAddressDto {

    id: PersonEmailAddressDtoId;

    emailAddress: EmailAddressDto;

    mainEmail: boolean;

}