import { Injectable } from '@angular/core';
import { PersonAdditionalHardSkillDto } from '../../../../types/dto/person/related/skill/hard/person-additional-hard-skill.dto';
import { PersonAdditionalHardSkillDtoId } from '../../../../types/dto/person/related/reference/skill/person-additional-hard-skill-dto-id';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
@Injectable({
  providedIn: 'root'
})
export class PersonAdditionalHardSkillService extends BasePersonRelatedCrudService<
  PersonAdditionalHardSkillDto,
  PersonAdditionalHardSkillDtoId,
  number
> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-additional-hard-skills") in your Spring Controller
    super('api/v1/person-additional-hard-skills');
  }

}
