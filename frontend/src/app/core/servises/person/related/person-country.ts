import { Injectable } from '@angular/core';

import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonCountryDto } from '../../../../types/dto/person/related/contact/country/person-country-dto';
import { PersonCountryDtoId } from '../../../../types/dto/person/related/reference/contact/person-country-dto-id';
import { ServiceResult } from '../../../../types/models/service-result';
import { EntityModel } from '../../../models/hateoas-models';

@Injectable({
  providedIn: 'root'
})
export class PersonCountryService 
  extends BasePersonRelatedCrudService
      <PersonCountryDto,
      PersonCountryDtoId,
      string, 
      ServiceResult<EntityModel<PersonCountryDto>>
      >
 {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-countries") in your Spring Controller
    super('api/v1/person-countries');
  }

}
