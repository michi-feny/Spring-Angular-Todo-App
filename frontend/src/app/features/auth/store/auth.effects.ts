import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import * as AuthActions from './auth.actions';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/servises/AuthService';
import { TokenData, AuthResponse } from '../../../types/dto/response/auth.response';
import decodeJwt, { DecodedUser } from '../../../utils/decodeJwt';
import { getCookieValue } from '../../../utils/getCookieValue';
import { addFlashMessage } from '../../../store/fleshMessage/fleshMessage.actions';
import { FlashMessageState, MessageType } from '../../../store/fleshMessage/fleshMessage.models';
import { Store } from '@ngrx/store';


@Injectable()
export class AuthEffects {
    private actions$ = inject(Actions);
    private authService = inject(AuthService);
    private router = inject(Router);
    private store = inject(Store<FlashMessageState>);

    private deleteClientCookie(name: string, path: string = '/') {
        document.cookie = `${name}=; Max-Age=0; path=${path}; domain=${window.location.hostname}`;
    }

    private mapTokenDataToAuthResponse(response: TokenData): AuthResponse | { error: any } {
        const jwtHpValue: string|null = getCookieValue('jwt_hp');
        
        if(!jwtHpValue) {
            return { error: { message: 'Token nicht vorhanden im Cookie' } };;
        }
    
        const decodedUser = decodeJwt(jwtHpValue);
        if(!decodedUser) {
            return { error: { message: 'Token-Dekodierung fehlgeschlagen.' } };
        }
    
        return {
            token: jwtHpValue,
            accessTokenExp: response.accessTokenExp,
            refreshTokenExp: response.refreshTokenExp,
            user: {
                id: decodedUser.id,
                name: decodedUser.name,
                email: decodedUser.sub,
                roles: decodedUser.roles
            }
        };
    }
    login$ = createEffect(
        () => this.actions$.pipe(
            ofType(AuthActions.login),
            switchMap((action) => this.authService.login(action.request).pipe(
                map((response: TokenData) => {
                    const mappedResponse = this.mapTokenDataToAuthResponse(response);

                    if ('error' in mappedResponse) {
                        return AuthActions.loginFailure(mappedResponse);
                    }

                    return AuthActions.loginSuccess({ response: mappedResponse });
                }),
                catchError((error) => {
                    if (error.status === 401) {
                        this.store.dispatch(addFlashMessage({
                            messageType: MessageType.Danger,
                            text: error.error.detail,
                            timeout: 8000
                        }));
                    }
                    return of(AuthActions.loginFailure({ error }));
                })
            ))
        )
    );

    authSuccess$ = createEffect(
        () => this.actions$.pipe(
            ofType(AuthActions.loginSuccess),
            tap((action) => {
                localStorage.setItem('refreshTokenExp', action.response.refreshTokenExp.toString());
                this.router.navigate(['/todos']);
            })
        ),
        { dispatch: false }
    );

    refresh$ = createEffect(
        () => this.actions$.pipe(
            ofType(AuthActions.refresh),
            switchMap((action) => this.authService.refreshToken().pipe(
                map((response: TokenData) => {
                    const mappedResponse = this.mapTokenDataToAuthResponse(response);

                    if ('error' in mappedResponse) {
                        return AuthActions.loginFailure(mappedResponse);
                    }

                    return AuthActions.refreshSuccess({ response: mappedResponse });
                }),
                catchError((error) => of(AuthActions.refreshFailure({ error })))
            ))
        )
    );

    refreshSuccess$ = createEffect(
        () => this.actions$.pipe(
            ofType(AuthActions.refreshSuccess),
            tap((action) => {
                localStorage.setItem('refreshTokenExp', action.response.refreshTokenExp.toString());
            })
        ),
        { dispatch: false }
    );

    logout$ = createEffect(() => 
        this.actions$.pipe(
            ofType(AuthActions.logout),
            tap(() => {
                this.deleteClientCookie('jwt_hp');
            }),
            switchMap((action) => 
                this.authService.logout().pipe(
                    map((response) => AuthActions.logoutSuccess()),
                    catchError((error) => of(AuthActions.logoutFailure({ error }))
                )
            ))
        )
    );

    logoutSuccess$ = createEffect(() => 
        this.actions$.pipe(
            ofType(AuthActions.logoutSuccess),
            tap(() => {
                localStorage.removeItem('refreshTokenExp');
                this.router.navigate(['/auth/login']);
            })
        ),
        { dispatch: false } 
    );

    loadToken$ = createEffect(() => 
        this.actions$.pipe(
            ofType(AuthActions.loadTokenFromStorage),
            map(() => {
                const accessToken = getCookieValue('jwt_hp');

                let tokenExpiresAt: number = 0;
                let refreshTokenExp: number | null | string = Number(localStorage.getItem('refreshTokenExp'));
                let tokenPayload: null|DecodedUser  = null;

                if(accessToken) {
                    tokenPayload = decodeJwt(accessToken);
                    tokenExpiresAt = Number(tokenPayload?.exp ?? null);
                }

                return AuthActions.tokenLoaded({ 
                    token: accessToken || null, 
                    accessTokenExp: tokenExpiresAt > 0 ? tokenExpiresAt : null, 
                    refreshTokenExp: refreshTokenExp > 0 ? refreshTokenExp : null,
                    user: tokenPayload !== null 
                    ? {
                        id: tokenPayload.id,
                        name: tokenPayload.name,
                        email: tokenPayload.sub,
                        roles: tokenPayload.roles
                    }
                    : null
                });
            })
        )
    );

    signUp$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.signUp),
            switchMap((action) =>
                this.authService.signUp(action.request).pipe(
                    map((response) => AuthActions.signUpSuccess({ response })),
                    catchError((error) => of(AuthActions.signUpFailure({ error })))
                )
            )
        )
    );

    signUpSuccessNavigation$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.signUpSuccess),
            tap(action => {

                this.store.dispatch(addFlashMessage({ 
                    messageType: MessageType.Success,
                    text: action.response.message,
                    timeout: 8000,
                    keepAfterNavigation: true
                }));

                this.router.navigate(['/auth/login']);
            })
        ),
        { dispatch: false }
    );
}