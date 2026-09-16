import { Injectable } from '@angular/core';

import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonSoftSkillDto } from '../../../../types/dto/person/person-skill.dto';
import { PersonSoftSkillDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonSoftSkillService extends BasePersonRelatedCrudService<
  PersonSoftSkillDto,
  PersonSoftSkillDtoId,
  string
> {

  constructor() {
    super('person-soft-skills');
  }

}
