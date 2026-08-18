import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CollectionModel, EntityModel } from '../../models/hateoas-models';
import { ApiSuccessResponse } from  '../../models/api-success-response';
import { BaseCrudService } from '../../servises/baseCrud/base-crud.service';

// Ensure these paths match where you generated your DTO interfaces
import { PersonData } from '../../../types/dto/person/person-data';
import { PersonOverview } from '../../../types/dto/person/person-overview';

@Injectable({
  providedIn: 'root'
})
export class PersonService extends BaseCrudService<PersonData, number> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/persons") in your Spring Controller
    super('api/v1/persons');
  }

  /**
   * GET /api/v1/persons/{id}/overview
   * Fetches the composite overview structure for a specific person.
   */
  public getOverview(id: number): Observable<ApiSuccessResponse<PersonOverview>> {
    return this.http.get<ApiSuccessResponse<PersonOverview>>(`${this.resourceUrl}/${id}/overview`);
  }

  /**
   * GET /api/v1/persons/search/firstName?firstName=...
   */
  public searchByFirstName(firstName: string): Observable<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> {
    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>>(
      `${this.resourceUrl}/search/firstName`, 
      { params: new HttpParams().set('firstName', firstName) }
    );
  }

  /**
   * GET /api/v1/persons/search/lastName?lastName=...
   */
  public searchByLastName(lastName: string): Observable<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> {
    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>>(
      `${this.resourceUrl}/search/lastName`, 
      { params: new HttpParams().set('lastName', lastName) }
    );
  }

  /**
   * GET /api/v1/persons/search/birthDate?birthDate=...
   * Expects an ISO Date String (YYYY-MM-DD).
   */
  public searchByBirthDate(birthDate: string): Observable<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> {
    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>>(
      `${this.resourceUrl}/search/birthDate`, 
      { params: new HttpParams().set('birthDate', birthDate) }
    );
  }

  /**
   * GET /api/v1/persons/search/socialRecordNumber?socialRecordNumber=...
   */
  public searchBySocialRecordNumber(socialRecordNumber: number): Observable<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> {
    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>>(
      `${this.resourceUrl}/search/socialRecordNumber`, 
      { params: new HttpParams().set('socialRecordNumber', socialRecordNumber.toString()) }
    );
  }

  /**
   * GET /api/v1/persons/search
   * Combined filter search handling optional parameters.
   */
  public searchByFilter(filters: {
    firstName?: string;
    lastName?: string;
    birthDate?: string;
    socialRecordNumber?: number;
  }): Observable<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> {
    let params = new HttpParams();

    if (filters.firstName) {
      params = params.set('firstName', filters.firstName);
    }
    if (filters.lastName) {
      params = params.set('lastName', filters.lastName);
    }
    if (filters.birthDate) {
      params = params.set('birthDate', filters.birthDate);
    }
    if (filters.socialRecordNumber !== undefined && filters.socialRecordNumber !== null) {
      params = params.set('socialRecordNumber', filters.socialRecordNumber.toString());
    }

    return this.http.get<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>>(
      `${this.resourceUrl}/search`, 
      { params }
    );
  }
}
