import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { catchError, filter, Observable, switchMap, take, tap, throwError } from "rxjs";
import { AuthState } from "../../features/auth/store/auth.models";
import { inject } from "@angular/core";
import { environment } from "../../../environments/environment";
import { Store } from "@ngrx/store";
import { selectIsRefreshing } from "../../features/auth/store/auth.selector";
import * as AuthActions from '../../features/auth/store/auth.actions';

const EXCLUDED_URLS = [
    `${environment.apiUrl}auth/signUp`,
    `${environment.apiUrl}auth/authenticate`,
    `${environment.apiUrl}auth/resetPassword`,
    `${environment.apiUrl}auth/validateResetToken`,
];

export const refreshInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
    const store = inject(Store<AuthState>); 
  
    const isExcluded = EXCLUDED_URLS.some(url => req.url.includes(url));

    if (isExcluded) {
        return next(req);
    }

    const reqWithCredentials = req.clone({
        withCredentials: true 
    });

    return next(reqWithCredentials).pipe(
        catchError(error => {
            // 1. Nur 401-Fehler behandeln
            if (error.status !== 401) {
                return throwError(() => error);
            }

            // 2. Single-Flight-Prüfung: Prüfen, ob Refresh bereits läuft
            return store.select(selectIsRefreshing).pipe(
                take(1),
                switchMap(isRefreshing => {
                    if (!isRefreshing) {
                        // Refresh ist nicht aktiv: Starte den Refresh-Vorgang (Dispatch Action)
                        store.dispatch(AuthActions.refresh());

                        // Warte auf den Abschluss (isRefreshing wird im Reducer wieder auf false gesetzt)
                        return store.select(selectIsRefreshing).pipe(
                            filter(v => v === false),
                            take(1),
                            // Sende den ursprünglichen Request erneut (muss geklont werden, falls der erste Interceptor 
                            // ihn nicht vollständig klont, aber hier klonen wir zur Sicherheit nur den Stream-Kontext)
                            switchMap(() => next(req.clone({ withCredentials: true })))
                        );
                    } else {
                        // Refresh läuft bereits: Nur warten
                        return store.select(selectIsRefreshing).pipe(
                            filter(v => v === false),
                            take(1),
                            // Sende den ursprünglichen Request erneut
                            switchMap(() => next(req.clone({ withCredentials: true })))
                        );
                    }
                }),
                catchError(refreshError => {
                    // 3. Fehlerbehandlung bei fehlgeschlagenem Refresh (z.B. Refresh Token abgelaufen)
                    store.dispatch(AuthActions.logout());
                    return throwError(() => refreshError); // Fehler weitergeben
                })
            );
        })
    );
};