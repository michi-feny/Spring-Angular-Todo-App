import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonAdditionalHardSkillDto } from '../../../../types/dto/person/person-skill.dto';
import { PersonAdditionalHardSkillDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonAdditionalHardSkillService extends BasePersonRelatedCrudService<
  PersonAdditionalHardSkillDto,
  PersonAdditionalHardSkillDtoId,
  string
> {
  constructor() {
    super('person-additional-hard-skills');
  }
}
