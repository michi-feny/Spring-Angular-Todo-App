import { Injectable } from '@angular/core';
import { PersonProfessionQualificationDto } from '../../../../types/dto/person/related/skill/hard/person-profession-qualification.dto';
import { PersonProfessionQualificationDtoId } from '../../../../types/dto/person/related/reference/skill/person-profession-qualification-dto-id';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';

@Injectable({
  providedIn: 'root'
})
export class PersonProfessionQualificationService extends BasePersonRelatedCrudService<
  PersonProfessionQualificationDto,
  PersonProfessionQualificationDtoId,
  number
> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-profession-qualifications") in your Spring Controller
    super('api/v1/person-profession-qualifications');
  }

}
