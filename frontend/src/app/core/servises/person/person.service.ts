import { Injectable } from '@angular/core';

import { BaseCrudService }
from '../base-crud/base-crud.service';

import { PersonDto }
from '../../../types/dto/person/person.dto';


@Injectable({
    providedIn: 'root'
})
export class PersonService
    extends BaseCrudService<PersonDto> {


    constructor() {

        super('person');

    }

}