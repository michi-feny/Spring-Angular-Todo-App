import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { SignUp } from './features/auth/signUp/signUp';
import { ForgotPassword } from './features/auth/forgot-password/forgot-password';
import { ResetPasswordFlow } from './features/auth/reset-password-flow/reset-password-flow';

export const routes: Routes = [
    {
        component: Login,
        path: 'auth/login',
        title: 'Login'
    },
    {
        component: SignUp,
        path: 'auth/signup',
        title: 'Register'
    },
    { 
        component: ForgotPassword,
        path: 'auth/forgotPassword',
        title: 'Forgot Password'
    },
    {
        path: 'auth/resetPassword', 
        component: ResetPasswordFlow,
        title: 'Reset Password'
    }
];
