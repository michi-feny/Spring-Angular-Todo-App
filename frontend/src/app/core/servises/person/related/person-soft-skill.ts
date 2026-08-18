import { Injectable } from '@angular/core';
import { PersonSoftSkillDto } from '../../../../types/dto/person/related/skill/soft/person-soft-skill-dto';
import { PersonSoftSkillDtoId } from '../../../../types/dto/person/related/reference/skill/person-soft-skill-dto-id';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';

@Injectable({
  providedIn: 'root'
})
export class PersonSoftSkillService extends BasePersonRelatedCrudService<
  PersonSoftSkillDto,
  PersonSoftSkillDtoId,
  number
> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-soft-skills") in your Spring Controller
    super('api/v1/person-soft-skills');
  }

}
