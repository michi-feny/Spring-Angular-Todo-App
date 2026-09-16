import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonProfessionQualificationDto } from '../../../../types/dto/person/person-skill.dto';
import { PersonProfessionQualificationDtoId } from '../../../../types/dto/person/person-id.dto';

@Injectable({
  providedIn: 'root'
})
export class PersonProfessionQualificationService extends BasePersonRelatedCrudService<
  PersonProfessionQualificationDto,
  PersonProfessionQualificationDtoId,
  string
> {

  constructor() {
    super('person-profession-qualifications');
  }

}
