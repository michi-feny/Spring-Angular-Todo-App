import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { InputTypesEnum } from '../../../types/InputTypesEnum';
import { FormControl as BootstrapFormControl } from '../../../shared/components/bootstrap/form-control/form-control';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { CustomValidators } from '../../../shared/validator/CustomValidators';
import { FormControlPipe } from '../../../shared/pipes/form-control.pipe';
import { ResetPasswordForm as ResetPasswordFormType } from '../../../types/forms/auth-forms';

@Component({
    selector: 'app-reset-password-form',
    imports: [ReactiveFormsModule, BootstrapFormControl, TranslateModule, FormControlPipe],
    templateUrl: './reset-password.html',
    styleUrl: './reset-password.css',
})
export class ResetPasswordForm {
    private fb = inject(FormBuilder);
    public readonly InputTypesEnum = InputTypesEnum;

    @Input() userId: number|null = null;
    @Input() token: string|null = null;
    @Input() loading: boolean = false;
    @Output() submitNewPassword = new EventEmitter<FormGroup<ResetPasswordFormType>>();

    public resetPasswordForm: FormGroup<ResetPasswordFormType> = this.fb.group<ResetPasswordFormType>(
        {
            password: this.fb.control('', { 
                validators: [Validators.required, CustomValidators.passwordStrength(10)], 
                nonNullable: true 
            }),
            confirmPassword: this.fb.control('', {
                validators: [Validators.required],
                nonNullable: true
            }),
        },
        {
          validators: [CustomValidators.equalsTo('password', 'confirmPassword', 'FORM.PASSWORD')] 
        }
    );

    handleSubmit(): void {
        if (this.resetPasswordForm.invalid) {
            this.resetPasswordForm.markAllAsTouched();
            return;
        }

        this.submitNewPassword.emit(this.resetPasswordForm);
    }
}
