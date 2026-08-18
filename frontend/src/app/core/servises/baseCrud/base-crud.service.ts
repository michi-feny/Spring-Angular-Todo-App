import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CollectionModel, EntityModel } from '../../models/hateoas-models';

import { BaseService } from './base.service';
import { ApiSuccessResponse } from '../../models/api-success-response';
// BaseCrudService provides: 
/**
   * GET /api/v1/{resource}
   * Retrieves all items wrapped in a HATEOAS CollectionModel.
   */
 /**
   * GET /api/v1/{resource}/{id}
   * Retrieves a single item by ID, wrapped in an EntityModel.
   */
   /**
   * POST /api/v1/{resource}
   */
  /**
   * DELETE /api/v1/{resource}/{id}
   * Deletes an entity by ID. Does not return a data body.
   */
export abstract class BaseCrudService<DTO, ID = number> extends BaseService {
  protected readonly http = inject(HttpClient);
  protected readonly resourceUrl: string;

  protected constructor(resource: string) {
    super();
    this.resourceUrl = this.buildUrl(resource);
  }


  /**
   * GET /api/v1/{resource}
   * Retrieves all items wrapped in a HATEOAS CollectionModel.
   */
  public getAll(): Observable<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>> {
    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>>(this.resourceUrl);
  }

  /**
   * GET /api/v1/{resource}/{id}
   * Retrieves a single item by ID, wrapped in an EntityModel.
   */
  public getById(id: ID): Observable<ApiSuccessResponse<EntityModel<DTO>>> {
    return this.http.get<ApiSuccessResponse<EntityModel<DTO>>>(`${this.resourceUrl}/${id}`);
  }

 /**
   * POST /api/v1/{resource}
   */
  public create(dto: DTO): Observable<ApiSuccessResponse<EntityModel<DTO>>> {
    // FIXED: Added space between > > to prevent TS parsing error
    return this.http.post<ApiSuccessResponse<EntityModel<DTO>> >(this.resourceUrl, dto);
  }

 /**
   * PUT /api/v1/{resource}/{id}
   */
  public update(id: ID, dto: DTO): Observable<ApiSuccessResponse<EntityModel<DTO>>> {
    // FIXED: Added space between > > to prevent TS parsing error
    return this.http.put<ApiSuccessResponse<EntityModel<DTO>> >(`${this.resourceUrl}/${id}`, dto);
  }

  /**
   * DELETE /api/v1/{resource}/{id}
   * Deletes an entity by ID. Does not return a data body.
   */
  public delete(id: ID): Observable<ApiSuccessResponse<void>> {
    return this.http.delete<ApiSuccessResponse<void>>(`${this.resourceUrl}/${id}`);
  }
}
