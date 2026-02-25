import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormControl as BootstrapFormControl } from '../../../shared/components/bootstrap/form-control/form-control';
import { InputTypesEnum } from '../../../types/InputTypesEnum';
import { Store } from '@ngrx/store';
import { login } from './../store/auth.actions';
import { RouterLink } from '@angular/router';
import { AuthState } from '../store/auth.models';
import { FlashMessage } from '../../../shared/components/bootstrap/flash-message/flash-message';
import { TranslateModule } from '@ngx-translate/core';
import { CustomValidators } from '../../../shared/validator/CustomValidators';
import { isLoading, selectAuthError } from '../store/auth.selector';
import { LoginForm } from '../../../types/forms/auth-forms';
import { FormControlPipe } from '../../../shared/pipes/form-control.pipe';
import { ServerSideValidationDirective } from '../../../shared/directives/server-side-validation.directive';

@Component({
    selector: 'app-login',
    imports: [
        ReactiveFormsModule,
        CommonModule,
        BootstrapFormControl,
        RouterLink,
        FlashMessage,
        TranslateModule,
        FormControlPipe,
        ServerSideValidationDirective
    ],
    templateUrl: './login.html',
    styleUrl: './login.css',
})
export class Login {
    public readonly InputTypesEnum = InputTypesEnum;
    private store = inject(Store<AuthState>);
    private fb = inject(FormBuilder);
    public isLoading$ = this.store.select(isLoading);
    public selectAuthError = selectAuthError;
    public loginForm: FormGroup<LoginForm> = this.fb.group<LoginForm>({
        email: this.fb.control('', {
            validators: [Validators.required, Validators.email],
            nonNullable: true 
        }),
        password: this.fb.control('', { 
            validators: [Validators.required, CustomValidators.passwordStrength(10)], 
            nonNullable: true 
        }),
    });

    handleSubmit(): void {
        if (this.loginForm.invalid) {
            this.loginForm.markAllAsTouched();
            return;
        }

        this.store.dispatch(login({request: this.loginForm.getRawValue()}));
    }
}
