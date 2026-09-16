export interface CountryDto {
  id?: number | null;
  code: string;
  name: string;
  language?: string | null;
}

export interface AddressDto {
  id?: number | null;
  street: string;
  houseNumber: string;
  zipCode: string;
  city: string;
  nationalityId: number;
}

export interface CompanyDto {
  id?: number | null;
  name: string;
  legalForm: string;
  address?: AddressDto | null;
}

export interface EducationInstitutionDto {
  id?: number | null;
  name: string;
  address: AddressDto;
}

export interface EmailAddressDto {
  id?: number | null;
  emailAddress: string;
}

export interface PhoneNumberDto {
  id?: number | null;
  phoneNumber: string;
  countryCode: string;
  fullNumber?: string | null;
  nationalityId: number;
}

export interface LocalDateDurationDto {
  startDate?: string | null;
  endDate?: string | null;
}
