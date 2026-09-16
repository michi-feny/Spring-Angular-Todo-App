import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonDegreeDto } from '../../../../types/dto/person/person-skill.dto';
import { PersonDegreeDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonDegreeService extends BasePersonRelatedCrudService<
  PersonDegreeDto,
  PersonDegreeDtoId,
  string
> {

  constructor() {
    super('person-degrees');
  }

}
