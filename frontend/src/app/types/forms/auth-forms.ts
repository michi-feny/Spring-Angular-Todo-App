import { FormControl } from '@angular/forms';

export interface SignUpForm {
  name: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  confirmPassword: FormControl<string>;
  role: FormControl<string>;
}

export interface ForgotPasswordForm {
    email: FormControl<string>;
}

export interface LoginForm {
    email: FormControl<string>;
    password: FormControl<string>;
}

export interface ResetPasswordForm {
    password: FormControl<string>;
    confirmPassword: FormControl<string>;
  }