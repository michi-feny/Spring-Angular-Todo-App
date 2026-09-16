import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { ApiSuccessResponse } from '../models/api-success-response';
import { environment } from '../../../environments/environment';
import { CountryDto } from '../../types/dto/common/common.dto';


@Injectable({
  providedIn: 'root',
})
export class CountryService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'countries';

  fetchAllCountries(): Observable<CountryDto[]> {
    return this.http.get<ApiSuccessResponse<CountryDto[]>>(this.apiUrl).pipe(
      map(response => response.data)
    );
  }
}
