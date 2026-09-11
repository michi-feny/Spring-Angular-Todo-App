import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ApiSuccessResponse } from '../../../../core/models/api-success-response';
import { CountryService } from '../../../../core/servises/country-service';
import { PersonCountryService } from '../../../../core/servises/person/related/person-country';
import { ApiResponse } from '../../../../types/ApiResponse';
import { CountryDto } from '../../../../types/dto/country.dto';
import { PersonCountryDto } from '../../../../types/dto/person/related/contact/country/person-country-dto';
import { ServiceResult } from '../../../../types/models/service-result';

@Component({
  selector: 'app-person-country-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './person-country-form.html',
  styleUrl: './person-country-form.css',
})
export class PersonCountryFormComponent implements OnInit {
  private personCountryService = inject(PersonCountryService);
  private countryService = inject(CountryService);
  private http = inject(HttpClient);

  countries = signal<CountryDto[]>([]);
  countrySearchQuery = signal<string>('');

  filteredCountries = computed(() => {
    const query = this.countrySearchQuery().toLowerCase().trim();
    const allCountries = this.countries();
    if (!query) return allCountries;
    return allCountries.filter(c => c.name.toLowerCase().includes(query));
  });

  formData = signal<PersonCountryDto>({
    id: { personId: 1, countryId: 0 },
    country: { id: 0, name: '', code: '' }, // Initialize with dummy data
    mainCountry: false
  });

  // Bound directly to the select element
  selectedCountryId = signal<number | undefined>(undefined);

  showOverwriteModal = signal<boolean>(false);
  errorMessage = signal<string>('');
  repairPayload = signal<PersonCountryDto | null>(null);
  repairUrl = signal<string>('');

  ngOnInit(): void {
    this.countryService.fetchAllCountries().subscribe({
      next: (data) => this.countries.set(data),
      error: () => console.error('Failed to load countries')
    });
  }

  submit() {
    const selectedId = this.selectedCountryId();
    if (!selectedId) {
      alert("Please select a country");
      return;
    }

    // Assemble payload right before submit so it has the correct country mapping
    const payload = this.formData();
    payload.country.id = selectedId;
    
    this.personCountryService.create(payload).subscribe({
      next: (res: ApiResponse<ServiceResult<PersonCountryDto>>) => {
        const message = (res.message && res.message.trim() !== '') ? res.message : 'Successfully assigned country!';
        alert(message);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 409 && err.error?.data?.status === 'REJECTED') {
          const apiResponse = err.error as ApiResponse<ServiceResult<PersonCountryDto>>;
          const rejectedDto = apiResponse.data!.value;
          const repairLink = apiResponse.links?.find(l => l.rel === 'repair')?.href;

          if (repairLink) {
            this.repairPayload.set(rejectedDto);
            this.repairUrl.set(repairLink);
            const serverMsg = apiResponse.message || apiResponse.data?.violations?.[0]?.message;
            this.errorMessage.set((serverMsg && serverMsg.trim() !== '') ? serverMsg : 'A main country already exists for this person.');
            this.showOverwriteModal.set(true);
          }
        } else {
          alert('An unexpected error occurred.');
        }
      }
    });
  }

  confirmOverwrite() {
    const url = this.repairUrl();
    const payload = this.repairPayload();

    if (!url || !payload) return;

    this.http.put<ApiSuccessResponse<ServiceResult<PersonCountryDto>>>(url, payload).subscribe({
      next: (res: ApiSuccessResponse<ServiceResult<PersonCountryDto>>) => {
        const message = (res.message && res.message.trim() !== '') ? res.message : 'Successfully updated existing main country!';
        alert(message);
        this.showOverwriteModal.set(false);
      },
      error: (err: HttpErrorResponse) => {
        alert('Update failed.');
      }
    });
  }
}
