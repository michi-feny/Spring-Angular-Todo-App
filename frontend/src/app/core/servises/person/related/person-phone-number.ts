import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { EntityModel } from '../../../models/hateoas-models';
import { ServiceResult } from '../../../../types/models/service-result';
import { PersonPhoneNumberDto } from '../../../../types/dto/person/person-contact.dto';
import { PersonPhoneNumberDtoId } from '../../../../types/dto/person/person-id.dto';

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
    super('person-phone-numbers');
  }
}