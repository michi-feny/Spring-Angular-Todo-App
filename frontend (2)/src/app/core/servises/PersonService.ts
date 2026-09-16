import { inject, Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { delay, map, Observable, of } from "rxjs";
import { ApiResponse } from "../../types/ApiResponse";
import { Person } from "../../types/person";

@Injectable({
    providedIn: 'root'
})
export class PersonService {
    private apiUrl: string = `${environment.apiUrl}persons`;
    private http: HttpClient = inject(HttpClient);

    fetch(id: number): Observable<Person> {
        const mockPerson: Person = {
            id: id,
            socialRecordNumber: 1234567890,
            firstName: 'Max',
            lastName: 'Mustermann',
            birthDate: '1990-05-15',
        
            nationalitys: [
              {
                mainCountry: true,
                country: {
                  id: 1,
                  code: 'AT',
                  name: 'Österreich',
                  language: 'de'
                }
              }
            ],
        
            addresses: [
              {
                mainAddress: true,
                address: {
                  id: 10,
                  street: 'Hauptstraße',
                  houseNumber: '12a',
                  zipCode: '1010',
                  city: 'Wien',
                  country: {
                    id: 1,
                    code: 'AT',
                    name: 'Österreich',
                    language: 'de'
                  }
                }
              }
            ],
        
            phones: [
              {
                mainNumber: true,
                phoneNumber: {
                  id: 20,
                  countryCode: '+43',
                  phoneNumber: '6641234567',
                  fullNumber: '+436641234567'
                }
              }
            ],
        
            emails: [
              {
                mainEmail: true,
                emailAddress: {
                  id: 30,
                  emailAddress: 'max.mustermann@example.com'
                }
              }
            ],
        
            degrees: [
              {
                startDate: '2010-10-01',
                endDate: '2013-06-30',
                progressInPercent: 100,
                degree: {
                  id: 101,
                  name: 'Bachelor of Science',
                  level: 'BSc',
                  weight: 1,
                  preName: false,
                  postName: true
                },
                institution: {
                  id: 1000,
                  name: 'TU Wien'
                }
              },
              {
                startDate: '2013-10-01',
                endDate: '2015-06-30',
                progressInPercent: 100,
                degree: {
                  id: 102,
                  name: 'Master of Science',
                  level: 'MSc',
                  weight: 2,
                  preName: false,
                  postName: true
                },
                institution: {
                  id: 1000,
                  name: 'TU Wien'
                }
              }
            ],
        
            professions: [
              {
                startDate: '2016-01-15',
                endDate: null,
                certificateNumber: 'CERT-2016-99',
                professionQualification: {
                  id: 201,
                  name: 'Zertifizierter Software Architekt',
                  level: 'Senior',
                  weight: 1
                },
                educationInstitution: {
                  id: 2000,
                  name: 'iSAQB'
                }
              }
            ],
        
            additionalSkills: [
              {
                additionalHardSkill: {
                  id: 301,
                  name: 'Führerschein B',
                  category: 'DRIVING'
                }
              },
              {
                additionalHardSkill: {
                  id: 302,
                  name: 'Englisch',
                  level: 'C1',
                  category: 'LANGUAGE'
                }
              }
            ],
        
            softSkills: [
              {
                softSkill: {
                  id: 401,
                  name: 'Teamfähigkeit'
                }
              },
              {
                softSkill: {
                  id: 402,
                  name: 'Problemlösungskompetenz'
                }
              }
            ]
          };
        
          return of(mockPerson).pipe(delay(150));

        return this.http
            .get<ApiResponse<Person>>(`${this.apiUrl}/${id}`)
            .pipe(map((response) => response.data as Person));
    }

    create(person: Person): Observable<Person> {
        return this.http
            .post<ApiResponse<Person>>(this.apiUrl, person)
            .pipe(map((response) => response.data as Person));
    }

    update(person: Person): Observable<Person> {
        return this.http
        .put<ApiResponse<Person>>(`${this.apiUrl}/${person.id}`, person)
        .pipe(map((response) => response.data as Person));
    }
}