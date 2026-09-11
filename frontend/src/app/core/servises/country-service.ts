import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CountryDto } from '../../types/dto/country.dto';


@Injectable({
  providedIn: 'root',
})
export class CountryService {
  private http = inject(HttpClient);
  private apiUrl = '/api/v1/countries'; // Matches your backend base route structure

  fetchAllCountries(): Observable<CountryDto[]> {
    return this.http.get<CountryDto[]>(this.apiUrl);
  }
}
