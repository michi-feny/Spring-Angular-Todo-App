import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { SignUp } from './features/auth/signUp/signUp';
import { List as TodoList } from './features/todo/components/list/list';
import { authGuard } from './core/guard/auth.guard';
import { ForgotPassword } from './features/auth/forgot-password/forgot-password';
import { ResetPasswordFlow } from './features/auth/reset-password-flow/reset-password-flow';
import { UserMainActionTab } from './features/main-tab/user-main-action-tab/user-main-action-tab';
import { PersonList } from './features/person/components/person-list/person-list';

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
    },
    {
        component: PersonList,
        path: 'persons',
        canActivate: [authGuard],
        title: 'persons'
    }, 

];
