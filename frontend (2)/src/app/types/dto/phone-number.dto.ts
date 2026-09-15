export interface PhoneNumberDto {

  id?: number;
  phoneNumber: string;
  countryCode: string;
  fullNumber?: string | null;
  nationalityId: number;
}