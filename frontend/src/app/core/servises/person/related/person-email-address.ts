import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { EntityModel } from '../../../models/hateoas-models';
import { ServiceResult } from '../../../../types/models/service-result';
import { PersonEmailAddressDto } from '../../../../types/dto/person/person-contact.dto';
import { PersonEmailAddressDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonEmailAddressService extends BasePersonRelatedCrudService<
  PersonEmailAddressDto,
  PersonEmailAddressDtoId,
    string, 
    ServiceResult<EntityModel<PersonEmailAddressDto>>
> {

  constructor() {
    super('person-email-addresses');
  }

}