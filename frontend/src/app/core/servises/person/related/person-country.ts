import { Injectable } from '@angular/core';

import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { ServiceResult } from '../../../../types/models/service-result';
import { EntityModel } from '../../../models/hateoas-models';
import { PersonCountryDto } from '../../../../types/dto/person/person-contact.dto';
import { PersonCountryDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonCountryService extends BasePersonRelatedCrudService<
  PersonCountryDto,
  PersonCountryDtoId,
  string, 
  ServiceResult<EntityModel<PersonCountryDto>>
> {
  constructor() {
    super('person-countries');
  }
}
