import { Injectable } from '@angular/core';
import { PersonEmailAddressDto } from '../../../../types/dto/person/related/contact/mail/person-email-address.dto';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonEmailAddressDtoId } from '../../../../types/dto/person/related/reference/contact/person-email-address-dto-id';

@Injectable({
  providedIn: 'root'
})
export class PersonEmailAddressService extends BasePersonRelatedCrudService<
  PersonEmailAddressDto,
  PersonEmailAddressDtoId,
  number
> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-email-addresses") in your Spring Controller
    super('api/v1/person-email-addresses');
  }

}