
import { AddressDto, EmailAddressDto, PhoneNumberDto } from "../common/common.dto";
import { PersonAddressDtoId, PersonCountryDtoId, PersonEmailAddressDtoId, PersonPhoneNumberDtoId } from "./person-id.dto";

export interface PersonAddressDto {
  id: PersonAddressDtoId;
  address: AddressDto;
  mainAddress: boolean;
}

export interface PersonCountryDto {
  id: PersonCountryDtoId;
  mainCountry: boolean;
}

export interface PersonEmailAddressDto {
  id: PersonEmailAddressDtoId;
  emailAddress: EmailAddressDto;
  mainEmail: boolean;
}

export interface PersonPhoneNumberDto {
  id: PersonPhoneNumberDtoId;
  phoneNumber: PhoneNumberDto;
  mainPhoneNumber: boolean;
}