import { Injectable } from '@angular/core';
import { BasePersonRelatedCrudService } from '../../baseCrud/base-person-related-crud.service';
import { PersonAddressDto } from '../../../../types/dto/person/related/contact/address/person-address.dto';
import { PersonAddressDtoId } from '../../../../types/dto/person/related/reference/contact/person-address-dto-id';

@Injectable({
  providedIn: 'root'
})
// We pass <DTO, IDDTO, ID>. Assuming your IDs are numbers!
export class PersonAddressService extends BasePersonRelatedCrudService<PersonAddressDto, PersonAddressDtoId, number> {

  constructor() {
    // Maps exactly to @RequestMapping("/api/v1/person-addresses") in your Spring Controller
    super('api/v1/person-addresses');
  }

  // =========================================================================
  // 🎉 LOOK AT ALL THE ENDPOINTS THIS SERVICE AUTOMATICALLY HAS:
  // =========================================================================
  
  // FROM BaseCrudService:
  // - this.getAll()
  // - this.getById(id)
  // - this.create(dto)
  // - this.update(id, dto)
  // - this.delete(id)
  
  // FROM BasePersonRelatedCrudService:
  // - this.getByPersonId(personId)
  // - this.getIdsByPersonId(personId)
  // - this.getWithDetailsById(id)
  
  // If your PersonAddressController has ANY custom endpoints (like searchByCity), 
  // you would just write them right here, exactly like we did in PersonService!
}
