import { Component, inject, signal } from '@angular/core';
import { InputTypesEnum } from '../../../types/InputTypesEnum';
import { FormControl as BootstrapFormControl } from '../../../shared/components/bootstrap/form-control/form-control';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/servises/AuthService';
import { Store } from '@ngrx/store';
import { FlashMessage } from '../../../shared/components/bootstrap/flash-message/flash-message';
import { FlashMessageState, MessageType } from '../../../store/fleshMessage/fleshMessage.models';
import { addFlashMessage } from '../../../store/fleshMessage/fleshMessage.actions';
import { HttpErrorResponse } from '@angular/common/http';
import { TranslateModule } from '@ngx-translate/core';
import { ForgotPasswordForm } from '../../../types/forms/auth-forms';
import { FormControlPipe } from '../../../shared/pipes/form-control.pipe';
import { FormHelper } from '../../../shared/validator/FormHelper';

@Component({
    selector: 'app-forgot-password',
    imports: [ReactiveFormsModule, BootstrapFormControl, FlashMessage, TranslateModule, FormControlPipe],
    templateUrl: './forgot-password.html',
    styleUrl: './forgot-password.css',
})
export class ForgotPassword {
    private authService = inject(AuthService);
    private store = inject(Store<FlashMessageState>);
    private fb = inject(FormBuilder);
    public readonly isLoading = signal(false);
    public readonly InputTypesEnum = InputTypesEnum;
    public forgotPasswordForm = this.fb.group<ForgotPasswordForm>({
        email: this.fb.control('', {
            validators: [Validators.required, Validators.email],
            nonNullable: true
        }),
    });

    handleSubmit(): void {
        if (this.forgotPasswordForm.invalid) {
            this.forgotPasswordForm.markAllAsTouched();
            return;
        }

        this.isLoading.set(true);

        this.authService.forgotPassword(this.forgotPasswordForm.getRawValue()).subscribe({
            next: (response) => {
                this.isLoading.set(false);

                this.store.dispatch(addFlashMessage({
                    messageType: MessageType.Success,
                    text: response.message,
                    timeout: 8000
                }));
            },
            error: (err: HttpErrorResponse) => {
                this.isLoading.set(false);

                if(err.status === 422) {
                    FormHelper.setFormErrors(this.forgotPasswordForm, err.error.errors);
                }
            }
        });
    }
}
