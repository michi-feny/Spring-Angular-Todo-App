import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthState } from './auth.models';


export const selectAuthState = createFeatureSelector<AuthState>('auth');

export const selectIsAuthenticated = createSelector(
    selectAuthState,
    (state) => state.isAuthenticated
);

export const selectAuthUser = createSelector(
    selectAuthState,
    (state) => state.user
);

export const selectAuthToken = createSelector(
    selectAuthState,
    (state) => state.token
);

export const selectAuthIsLoading = createSelector(
    selectAuthState,
    (state) => state.isLoading
);

export const selectAuthError = createSelector(
    selectAuthState,
    (state) => state.error
);

export const selectAccessTokenExpiy = createSelector(
    selectAuthState,
    (state) => state.accessTokenExpiry
);

export const selectRefreshTokenExpiy = createSelector(
    selectAuthState,
    (state) => state.refreshTokenExpiry
);

export const selectIsRefreshing = createSelector(
    selectAuthState,
    (state) => state.isRefreshing
);

export const isLoading = createSelector(
    selectAuthState,
    (state) => state.isLoading
);