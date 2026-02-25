import { createReducer, on } from '@ngrx/store';
import * as AuthActions from './auth.actions';
import { AuthState, initialAuthState } from './auth.models';

export const authFeatureKey = 'auth';

export const authReducer = createReducer(
    initialAuthState,

    //--- Login/Sign Up Flow: Start ---
    on(AuthActions.login, AuthActions.signUp, (state: AuthState) => ({
        ...state,
        isLoading: true,
        error: null,
    })),

    on(AuthActions.refresh, (state: AuthState) => ({
        ...state,
        error: null,
        isRefreshing: true
    })),

    // --- Login/Sign Up Flow: Failure ---
    on(AuthActions.loginFailure, AuthActions.signUpFailure, AuthActions.refreshFailure, (state: AuthState, { error }) => ({
        ...state,
        user: null,
        token: null,
        isAuthenticated: false,
        isLoading: false,
        error: error,
        isRefreshing: false
    })),

    on(AuthActions.logoutFailure, (state: AuthState, { error }) => ({
        ...state,
        isLoading: false,
        error: error,
        isRefreshing: false
    })),

    // --- Login Flow: Success ---
    on(AuthActions.loginSuccess, AuthActions.refreshSuccess, (state: AuthState, { response }) => ({
        ...state,
        user: response.user ?? null,
        token: response.token,
        accessTokenExpiry: response.accessTokenExp,
        refreshTokenExpiry: response.refreshTokenExp,
        isAuthenticated: true,
        isLoading: false,
        error: null,
        isRefreshing: false
    })),

    on(AuthActions.signUpSuccess, (state: AuthState, action) => ({
        ...state,
        isLoading: false, 
        error: null,
    })),

    // --- Logout ---
    on(AuthActions.logoutSuccess, () => ({
        ...initialAuthState,
    })),

    // --- Token Initialization ---
    on(AuthActions.tokenLoaded, (state: AuthState, { token, accessTokenExp, refreshTokenExp, user  }) => {
        let isTokenExpired: boolean = true;

        if(accessTokenExp !== null) {
            const expTimeStampInMiliseconds = accessTokenExp * 1000;
            isTokenExpired = Date.now() > expTimeStampInMiliseconds;
        }

        return {
            ...state,
            token: token,
            accessTokenExpiry: accessTokenExp !== null && accessTokenExp > 0 ? accessTokenExp : null,
            refreshTokenExpiry: refreshTokenExp !== null && refreshTokenExp > 0 ? refreshTokenExp : null,
            isAuthenticated: !!token && !isTokenExpired,
            user: user
        }
    })
);