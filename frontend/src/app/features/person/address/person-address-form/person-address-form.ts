import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { PersonAddressService } from '../../../../core/servises/person/related/person-address';
import { PersonAddressDto } from '../../../../types/dto/person/related/contact/address/person-address.dto';
import { ServiceResult } from '../../../../types/models/service-result';
import { ApiResponse } from '../../../../types/ApiResponse';
import { CountryService } from '../../../../core/servises/country-service';
import { CountryDto } from '../../../../types/dto/country.dto';
import { ApiSuccessResponse } from '../../../../core/models/api-success-response';

@Component({
  selector: 'app-person-address-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './person-address-form.html',
  styleUrl: './person-address-form.css',
})
export class PersonAddressFormComponent {
  private addressService = inject(PersonAddressService);
  private countryService = inject(CountryService);
  private http = inject(HttpClient);

  // Dropdown Data State
  countries = signal<CountryDto[]>([]);
  countrySearchQuery = signal<string>('');

  filteredCountries = computed(() => {
    const query = this.countrySearchQuery().toLowerCase().trim();
    const allCountries = this.countries();
    if (!query) return allCountries;
    return allCountries.filter(c => c.name.toLowerCase().includes(query));
  });

  // Form State with complete AddressDto fields
  formData = signal<PersonAddressDto>({
    id: { personId: 1, addressId: 0 },
    address: { 
      street: '', 
      houseNumber: '', 
      zipCode: '', 
      city: '', 
      countryId: undefined 
    },
    mainAddress: false
  });

  showOverwriteModal = signal<boolean>(false);
  errorMessage = signal<string>('');
  repairPayload = signal<PersonAddressDto | null>(null);
  repairUrl = signal<string>('');

  ngOnInit():void{
    this.loadCountries();
  }
  loadCountries() {
    this.countryService.fetchAllCountries().subscribe({
      next: (data) => this.countries.set(data),
      error: () => console.error('Failed to load countries')
    });
  }

  submit() {
    this.addressService.create(this.formData()).subscribe({
      next: (res: ApiResponse<ServiceResult<PersonAddressDto>>) => {
        const message = (res.message && res.message.trim() !== '') 
          ? res.message 
          : 'Successfully created!';
        alert(message);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 409 && err.error?.data?.status === 'REJECTED') {
          const apiResponse = err.error as ApiResponse<ServiceResult<PersonAddressDto>>;
          const rejectedDto = apiResponse.data!.value;
          const repairLink = apiResponse.links?.find(l => l.rel === 'repair')?.href;

          if (repairLink) {
            this.repairPayload.set(rejectedDto);
            this.repairUrl.set(repairLink);
            const serverMsg = apiResponse.message || apiResponse.data?.violations?.[0]?.message;
            this.errorMessage.set((serverMsg && serverMsg.trim() !== '') 
              ? serverMsg 
              : 'A main address already exists for this person.');
            //this.errorMessage.set(apiResponse.data!.violations[0].message);
            this.showOverwriteModal.set(true);
          }
        } else {
          const serverMsg = err.error?.message;
          alert((serverMsg && serverMsg.trim() !== '') ? serverMsg : 'An unexpected error occurred.');
        }
      }
    });
  }

  confirmOverwrite() {
    const url = this.repairUrl();
    const payload = this.repairPayload();

    if (!url || !payload) return;//TODO: some Error handeling

    // url is already formatted as the string path (e.g. "/api/v1/person-addresses/1_2")
    // We pass an empty string or dummy ID to base class update since the full path is in the repair URL,
    // or call http.put directly if BaseCrudService expects a separated resource ID.
    this.http.put<ApiSuccessResponse<ServiceResult<PersonAddressDto>>>(url, payload).subscribe({
      next: (res: ApiSuccessResponse<ServiceResult<PersonAddressDto>>) => {
        const message = (res.message && res.message.trim() !== '') ? res.message : 'Successfully updated existing main address!';
        alert(message);
        this.showOverwriteModal.set(false);
      },
      error: (err: HttpErrorResponse) => {
        const serverMsg = err.error?.message;
        alert((serverMsg && serverMsg.trim() !== '') ? serverMsg : 'Update failed.');
      }
    });
  }
}
function computed(arg0: () => CountryDto[]) {
  throw new Error('Function not implemented.');
}

