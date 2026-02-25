import { createAction, props } from '@ngrx/store';
import { AuthResponse } from '../../../types/dto/response/auth.response';
import { ApiResponse, ApiResponseWithMessage } from '../../../types/ApiResponse';
import { LoginRequest, SignUpRequest } from '../../../types/dto/request/auth.requests';

// --- Login Flow ---
export const login = createAction(
    '[Auth] Login',
    props<{ request: LoginRequest }>()
);

export const loginSuccess = createAction(
    '[Auth] Login Success',
    props<{ response: AuthResponse }>()
);

export const loginFailure = createAction(
    '[Auth] Login Failure',
    props<{ error: any }>()
);

// --- Sign Up Flow ---
export const signUp = createAction(
    '[Auth] Sign Up',
    props<{ request: SignUpRequest }>()
);

export const signUpSuccess = createAction(
    '[Auth] Sign Up Success',
    props<{ response: ApiResponseWithMessage<null> }>()
);

export const signUpFailure = createAction(
    '[Auth] Sign Up Failure',
    props<{ error: any }>()
);

// --- Refresh Flow ---
export const refresh = createAction(
    '[Auth] Refresh'
);

export const refreshSuccess = createAction(
    '[Auth] Refresh Success',
    props<{ response: AuthResponse }>()
);

export const refreshFailure = createAction(
    '[Auth] Refresh Failure',
    props<{ error: any }>()
);


// --- Logout ---
export const logout = createAction('[Auth] Logout');

// --- Logout ---
export const logoutSuccess = createAction(
    '[Auth] Logout Success'
);

// --- Logout ---
export const logoutFailure = createAction(
    '[Auth] Logout Failure',
    props<{ error: any }>()
);

// --- Initialization ---
export const loadTokenFromStorage = createAction('[Auth] Load Token from Storage');

export const tokenLoaded = createAction(
    '[Auth] Token Loaded',
    props<{ 
        token: string | null,
        accessTokenExp: number | null,
        refreshTokenExp: number | null,
        user: User|null 
    }>()
);