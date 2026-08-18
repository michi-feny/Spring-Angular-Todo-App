import { Injectable } from '@angular/core';
import { PersonPhoneNumberDtoId } from '../../../../types/dto/person/related/reference/contact/person-phone-number-dto-id';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonPhoneNumberDto } from '../../../../types/dto/person/related/contact/phone/person-phone-number.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonPhoneNumberService extends BasePersonRelatedCrudService<
  PersonPhoneNumberDto,
  PersonPhoneNumberDtoId,
  number
> {

  constructor() {
    // Maps to @RequestMapping("/api/v1/person-phone-numbers") in your Spring Controller
    super('api/v1/person-phone-numbers');
  }
}