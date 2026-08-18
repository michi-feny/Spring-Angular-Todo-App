import { EmailAddressDto } from '../../../../email-address.dto';
import { PersonEmailAddressDtoId } from '../../reference/contact/person-email-address-dto-id';


export interface PersonEmailAddressDto {

    id?: PersonEmailAddressDtoId;

    email: EmailAddressDto;

    mainEmail: boolean;

}