import { EmailAddressDto } from '../../../email-address.dto';


export interface PersonEmailAddressDto {

    id?: number;

    email: EmailAddressDto;

    mainEmail: boolean;

}