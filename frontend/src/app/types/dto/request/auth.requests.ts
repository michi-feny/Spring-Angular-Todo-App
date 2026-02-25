export interface LoginRequest {
    email: string;
    password: string;
}

export interface SignUpRequest extends LoginRequest {
    name: string;
    confirmPassword: string
}


export interface ForgotPasswordRequest {
    email: string;
}

export interface ValidatePasswordResetTokenRequest {
    token: string;
}

export interface ResetPassworRequest {
    password: string;
    confirmPassword: string;
    userID: number;
    token: string
}