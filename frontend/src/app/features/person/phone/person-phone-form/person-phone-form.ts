import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ApiSuccessResponse } from '../../../../core/models/api-success-response';
import { CountryService } from '../../../../core/servises/country-service';
import { PersonPhoneNumberService } from '../../../../core/servises/person/related/person-phone-number';
import { ApiResponse } from '../../../../types/ApiResponse';
import { CountryDto } from '../../../../types/dto/country.dto';
import { PersonPhoneNumberDto } from '../../../../types/dto/person/related/contact/phone/person-phone-number.dto';
import { ServiceResult } from '../../../../types/models/service-result';
import { PhoneNumberUtil } from 'google-libphonenumber';

@Component({
  selector: 'app-person-phone-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './person-phone-form.html',
  styleUrl: './person-phone-form.css',
})
export class PersonPhoneFormComponent implements OnInit {
  private phoneService = inject(PersonPhoneNumberService);
  private countryService = inject(CountryService);
  private http = inject(HttpClient);
  // Initialize the Google Phone Number Utility
  private phoneUtil = PhoneNumberUtil.getInstance();


  // Dropdown Data State & Search Filter
  countries = signal<CountryDto[]>([]);
  countrySearchQuery = signal<string>('');

  filteredCountries = computed(() => {
    const query = this.countrySearchQuery().toLowerCase().trim();
    const allCountries = this.countries();
    if (!query) return allCountries;
    return allCountries.filter(c => c.name.toLowerCase().includes(query));
  });

  // Form State matching the updated Phone DTO
  formData = signal<PersonPhoneNumberDto>({
    id: { personId: 1, phoneNumberId: 0 },
    phoneNumber: { 
      phoneNumber: '', 
      countryCode: '', 
      countryId: undefined 
    },
    mainPhoneNumber: false
  });

  showOverwriteModal = signal<boolean>(false);
  errorMessage = signal<string>('');
  repairPayload = signal<PersonPhoneNumberDto | null>(null);
  repairUrl = signal<string>('');

  ngOnInit(): void {
    this.loadCountries();
  }

  loadCountries() {
    this.countryService.fetchAllCountries().subscribe({
      next: (data) => this.countries.set(data),
      error: () => console.error('Failed to load countries')
    });
  }

  /**
   * Automatically resolves the country code when the dropdown changes.
   */
  onCountryChange(selectedCountryId: number) {
    const selectedCountry = this.countries().find(c => c.id === selectedCountryId);
    let derivedCountryCode = '';

    if (selectedCountry && selectedCountry.code) { // Assumes CountryDto has 'isoCode' (e.g. 'AT')
      try {
        const callingCode = this.phoneUtil.getCountryCodeForRegion(selectedCountry.code.toUpperCase());
        derivedCountryCode = `+${callingCode}`;
      } catch (err) {
        console.error(`Could not determine country code for region ${selectedCountry.code}`, err);
      }
    }

    // Safely update the signal object immutably
    this.formData.update(data => ({
      ...data,
      phoneNumber: {
        ...data.phoneNumber,
        countryId: selectedCountryId,
        countryCode: derivedCountryCode
      }
    }));
  }

  submit() {
    this.phoneService.create(this.formData()).subscribe({
      next: (res: ApiResponse<ServiceResult<PersonPhoneNumberDto>>) => {
        const message = (res.message && res.message.trim() !== '') 
          ? res.message 
          : 'Successfully created!';
        alert(message);
      },
      error: (err: HttpErrorResponse) => {
        // Catch 409 Conflict where status is REJECTED
        if (err.status === 409 && err.error?.data?.status === 'REJECTED') {
          const apiResponse = err.error as ApiResponse<ServiceResult<PersonPhoneNumberDto>>;
          const rejectedDto = apiResponse.data!.value;
          const repairLink = apiResponse.links?.find(l => l.rel === 'repair')?.href;

          if (repairLink) {
            this.repairPayload.set(rejectedDto);
            this.repairUrl.set(repairLink);
            const serverMsg = apiResponse.message || apiResponse.data?.violations?.[0]?.message;
            this.errorMessage.set((serverMsg && serverMsg.trim() !== '') 
              ? serverMsg 
              : 'A main phone number already exists for this person.');
            
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

    if (!url || !payload) return; // TODO: Implement fallback error handling

    // Pass payload using the HATEOAS repair URL provided by the backend assembler
    this.http.put<ApiSuccessResponse<ServiceResult<PersonPhoneNumberDto>>>(url, payload).subscribe({
      next: (res: ApiSuccessResponse<ServiceResult<PersonPhoneNumberDto>>) => {
        const message = (res.message && res.message.trim() !== '') ? res.message : 'Successfully updated existing main phone number!';
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
