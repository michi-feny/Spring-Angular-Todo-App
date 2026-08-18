import { Observable } from 'rxjs';
import { BaseCrudService } from './base-crud.service';
import { ApiSuccessResponse } from '../../models/api-success-response';
import { CollectionModel, EntityModel } from '../../models/hateoas-models';


export abstract class BasePersonRelatedCrudService <DTO, IDDTO, ID = number> extends BaseCrudService<DTO, ID> {

  protected constructor(resource: string) {
    super(resource);
  }

  /**
   * GET /api/v1/{resource}/person/{personId}
   * Fetches all related entities for a specific person.
   */
  public getByPersonId(personId: number): Observable<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>> {
    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>> >(
      `${this.resourceUrl}/person/${personId}`
    );
  }

  /**
   * GET /api/v1/{resource}/person/{personId}/ids
   * Fetches a list of ID objects (IDDTO) associated with the person.
   * Java's List<IDDTO> translates to IDDTO[] in TypeScript.
   */
  public getIdsByPersonId(personId: number): Observable<ApiSuccessResponse<IDDTO[]>> {
    return this.http.get<ApiSuccessResponse<IDDTO[]>>(
      `${this.resourceUrl}/person/${personId}/ids`
    );
  }

  /**
   * GET /api/v1/{resource}/{id}/details
   * Fetches a specific entity by its ID with fully populated details.
   */
  public getWithDetailsById(id: ID): Observable<ApiSuccessResponse<EntityModel<DTO>>> {
    return this.http.get<ApiSuccessResponse<EntityModel<DTO>> >(
      `${this.resourceUrl}/${id}/details`
    );
  }
}