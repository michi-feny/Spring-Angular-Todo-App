import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { AppState } from '../../../store/app.state';
import { signUp } from '../store/auth.actions';
import { InputTypesEnum } from '../../../types/InputTypesEnum';
import { CommonModule } from '@angular/common';
import { FormControl as BootstrapFormControl } from '../../../shared/components/bootstrap/form-control/form-control';
import { RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { CustomValidators } from '../../../shared/validator/CustomValidators';
import { isLoading, selectAuthError } from '../store/auth.selector';
import { SignUpForm } from '../../../types/forms/auth-forms';
import { ServerSideValidationDirective } from '../../../shared/directives/server-side-validation.directive';
import { FormControlPipe } from '../../../shared/pipes/form-control.pipe';

@Component({
    selector: 'app-signUp',
    imports: [
        CommonModule,
        ReactiveFormsModule,
        BootstrapFormControl,
        RouterLink,
        TranslateModule,
        ServerSideValidationDirective,
        FormControlPipe
    ],
    templateUrl: './signUp.html',
    styleUrl: './signUp.css',
})
export class SignUp {
    private store = inject(Store<AppState>);
    private fb = inject(FormBuilder);
    public readonly selectAuthError = selectAuthError;
    public readonly InputTypesEnum = InputTypesEnum;
    public readonly roleOptions = [
        { value: "ROLE_USER", label: "User"},
        { value: "ROLE_ADMIN", label: "Admin"}
    ];

    public signUpForm: FormGroup<SignUpForm> = this.fb.group<SignUpForm>({
        name: this.fb.control('', { validators: [Validators.required], nonNullable: true }),
        email: this.fb.control('', { validators: [Validators.required, Validators.email], nonNullable: true }),
        password: this.fb.control('', { 
          validators: [Validators.required, CustomValidators.passwordStrength(10)], 
          nonNullable: true 
        }),
        confirmPassword: this.fb.control('', { validators: [Validators.required], nonNullable: true }),
        role: this.fb.control('', { 
          validators: [CustomValidators.choice({ choices: this.roleOptions.map(o => o.value) })], 
          nonNullable: true 
        })
      }, {
            validators: [CustomValidators.equalsTo('password', 'confirmPassword', 'FORM.PASSWORD')] 
      });

    public isLoading$ = this.store.select(isLoading);

    handleSignUp(): void {
        if (this.signUpForm.invalid) {
            this.signUpForm.markAllAsTouched();
            return;
        }

        this.store.dispatch(signUp({ request: this.signUpForm.getRawValue() }));
    }
}
