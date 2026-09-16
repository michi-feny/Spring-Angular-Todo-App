import { Injectable } from '@angular/core';
import { PersonPhoneNumberDtoId } from '../../../../types/dto/person/related/reference/contact/person-phone-number-dto-id';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonPhoneNumberDto } from '../../../../types/dto/person/related/contact/phone/person-phone-number.dto';
import { EntityModel } from '../../../models/hateoas-models';
import { ServiceResult } from '../../../../types/models/service-result';

@Injectable({
  providedIn: 'root'
})
export class PersonPhoneNumberService 
  extends BasePersonRelatedCrudService<
  PersonPhoneNumberDto,
  PersonPhoneNumberDtoId,
  string, 
  ServiceResult<EntityModel<PersonPhoneNumberDto>>
> {

  constructor() {
    // Maps to @RequestMapping("/api/v1/person-phone-numbers") in your Spring Controller
    super('person-phone-numbers');
  }
}