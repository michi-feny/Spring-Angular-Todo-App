import { Injectable } from '@angular/core';
import { PersonDegreeDto } from '../../../../types/dto/person/related/skill/hard/person-degree.dto';
import { PersonDegreeDtoId } from '../../../../types/dto/person/related/reference/skill/person-degree-dto-id';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';

@Injectable({
  providedIn: 'root'
})
export class PersonDegreeService extends BasePersonRelatedCrudService<
  PersonDegreeDto,
  PersonDegreeDtoId,
  number
> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-degrees") in your Spring Controller
    super('api/v1/person-degrees');
  }

}
