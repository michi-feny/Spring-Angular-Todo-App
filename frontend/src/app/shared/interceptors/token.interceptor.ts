import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable} from 'rxjs';
import { Store } from '@ngrx/store';
import { environment } from '../../../environments/environment';
import { AuthState } from '../../features/auth/store/auth.models';

const EXCLUDED_URLS = [
    `${environment.apiUrl}auth/signUp`
];

export const tokenInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
    const store = inject(Store<AuthState>); 
  
    const isExcluded = EXCLUDED_URLS.some(url => req.url.includes(url));

    if (isExcluded) {
        return next(req);
    }

    const clonedReq = req.clone({
        withCredentials: true 
    });

    return next(clonedReq);
};