import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { CollectionModel, EntityModel } from '../../../models/hateoas-models';
import { ServiceResult } from '../../../../types/models/service-result';
import { MergeWorkExperiencesRequestDto, PersonWorkExperienceDto } from '../../../../types/dto/person/person-skill.dto';
import { PersonWorkExperienceDtoId } from '../../../../types/dto/person/person-id.dto';
import { Observable } from 'rxjs';
import { ApiSuccessResponse } from '../../../models/api-success-response';

@Injectable({
  providedIn: 'root'
})
export class PersonWorkExperienceService 
  extends BasePersonRelatedCrudService<
  PersonWorkExperienceDto,
  PersonWorkExperienceDtoId,
  string, 
  ServiceResult<EntityModel<PersonWorkExperienceDto>>
> {

  constructor() {
    super('person-work-experiences');
  }

  public merge(dto: MergeWorkExperiencesRequestDto):  Observable<ApiSuccessResponse<CollectionModel<EntityModel<PersonWorkExperienceDto>>>> {
    return this.http.post<ApiSuccessResponse<CollectionModel<EntityModel<PersonWorkExperienceDto>>>>(
        `${this.resourceUrl}/merge`,
        dto
    );
  }
}