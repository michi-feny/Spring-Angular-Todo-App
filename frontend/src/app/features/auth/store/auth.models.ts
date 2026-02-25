export interface AuthState {
    user: User | null;
    token: string | null;
    accessTokenExpiry: number | null;
    refreshTokenExpiry: number | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    error: any;
    isRefreshing: boolean;
}

export const initialAuthState: AuthState = {
    user: null,
    token: null,
    accessTokenExpiry: null,
    refreshTokenExpiry: null,
    isAuthenticated: false,
    isLoading: false,
    error: null,
    isRefreshing: false,
};