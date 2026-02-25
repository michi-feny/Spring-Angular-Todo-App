export interface AuthResponse {
    token: string;
    refreshTokenExp: number;
    accessTokenExp: number
    user: User
}

export interface TokenData {
    refreshTokenExp: number;
    accessTokenExp: number
}


export interface ValidatePasswordResetTokenResponse {
    userId: number;
}