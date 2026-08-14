import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { BaseService } from '../base-service/base-service';


export abstract class BaseCrudService<T>
    extends BaseService {


    protected readonly http =
        inject(HttpClient);


    protected readonly resourceUrl: string;



    protected constructor(
        resource: string
    ) {

        super();

        this.resourceUrl =
            this.buildUrl(resource);

    }



    public getAll(): Observable<T[]> {

        return this.http.get<T[]>(
            `${this.resourceUrl}/all`
        );

    }



    public getById(
        id:number
    ): Observable<T> {

        return this.http.get<T>(
            `${this.resourceUrl}/${id}`
        );

    }



    public create(
        entity:T
    ): Observable<T> {

        return this.http.post<T>(
            this.resourceUrl,
            entity
        );

    }



    public update(
        id:number,
        entity:T
    ): Observable<T> {

        return this.http.put<T>(
            `${this.resourceUrl}/${id}`,
            entity
        );

    }



    public partialUpdate(
        id:number,
        entity:Partial<T>
    ): Observable<T> {

        return this.http.patch<T>(
            `${this.resourceUrl}/${id}`,
            entity
        );

    }



    public delete(
        id:number
    ): Observable<void> {

        return this.http.delete<void>(
            `${this.resourceUrl}/${id}`
        );

    }

}