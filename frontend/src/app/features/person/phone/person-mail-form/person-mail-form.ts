import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ApiSuccessResponse } from '../../../../core/models/api-success-response';
import { PersonEmailAddressService } from '../../../../core/servises/person/related/person-email-address';
import { ApiResponse } from '../../../../types/ApiResponse';
import { PersonEmailAddressDto } from '../../../../types/dto/person/related/contact/mail/person-email-address.dto';
import { ServiceResult } from '../../../../types/models/service-result';

@Component({
  selector: 'app-person-mail-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './person-mail-form.html',
  styleUrl: './person-mail-form.css',
})
export class PersonEmailFormComponent {
  private emailService = inject(PersonEmailAddressService);
  private http = inject(HttpClient);

  formData = signal<PersonEmailAddressDto>({
    id: { personId: 1, emailAddressId: 0 },
    emailAddress: { emailAddress: '' },
    mainEmail: false
  });

  showOverwriteModal = signal<boolean>(false);
  errorMessage = signal<string>('');
  repairPayload = signal<PersonEmailAddressDto | null>(null);
  repairUrl = signal<string>('');

  submit() {
    this.emailService.create(this.formData()).subscribe({
      next: (res: ApiResponse<ServiceResult<PersonEmailAddressDto>>) => {
        const message = (res.message && res.message.trim() !== '') ? res.message : 'Successfully created!';
        alert(message);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 409 && err.error?.data?.status === 'REJECTED') {
          const apiResponse = err.error as ApiResponse<ServiceResult<PersonEmailAddressDto>>;
          const rejectedDto = apiResponse.data!.value;
          const repairLink = apiResponse.links?.find(l => l.rel === 'repair')?.href;

          if (repairLink) {
            this.repairPayload.set(rejectedDto);
            this.repairUrl.set(repairLink);
            const serverMsg = apiResponse.message || apiResponse.data?.violations?.[0]?.message;
            this.errorMessage.set((serverMsg && serverMsg.trim() !== '') ? serverMsg : 'A main email address already exists for this person.');
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

    if (!url || !payload) return;

    this.http.put<ApiSuccessResponse<ServiceResult<PersonEmailAddressDto>>>(url, payload).subscribe({
      next: (res: ApiSuccessResponse<ServiceResult<PersonEmailAddressDto>>) => {
        const message = (res.message && res.message.trim() !== '') ? res.message : 'Successfully updated existing main email address!';
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
