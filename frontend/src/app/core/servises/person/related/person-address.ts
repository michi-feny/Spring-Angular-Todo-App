import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { ServiceResult } from '../../../../types/models/service-result';
import { EntityModel } from '../../../models/hateoas-models';
import { PersonAddressDto } from '../../../../types/dto/person/person-contact.dto';
import { PersonAddressDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})

export class PersonAddressService extends 
  BasePersonRelatedCrudService<
    PersonAddressDto,
    PersonAddressDtoId,
    string,
    ServiceResult<EntityModel<PersonAddressDto>>
  > {

  constructor() {
    super('person-addresses');
  }
}
