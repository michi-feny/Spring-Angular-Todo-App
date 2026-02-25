import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { map, Observable } from "rxjs";
import { TokenData, ValidatePasswordResetTokenResponse } from "../../types/dto/response/auth.response";
import { ApiResponseWithMessage } from "../../types/ApiResponse";
import { ForgotPasswordRequest, LoginRequest, ResetPassworRequest, SignUpRequest, ValidatePasswordResetTokenRequest } from "../../types/dto/request/auth.requests";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl: string = `${environment.apiUrl}`;
    private http: HttpClient = inject(HttpClient);

    login(loginRequest: LoginRequest): Observable<TokenData> {
        return this.http.post<ApiResponseWithMessage<TokenData>>(`${this.apiUrl}auth/authenticate`, loginRequest).pipe(
            map(response => response.data as TokenData)
        );
    }

    signUp(signUpRequest: SignUpRequest): Observable<ApiResponseWithMessage<null>> {
        return this.http.post<ApiResponseWithMessage<null>>(`${this.apiUrl}auth/signUp`, signUpRequest);
    }

    refreshToken(): Observable<TokenData> {
        return this.http.post<ApiResponseWithMessage<TokenData>>(`${this.apiUrl}auth/refresh`, {}).pipe(
            map(response => response.data as TokenData)
        );
    }

    logout(): Observable<ApiResponseWithMessage<null>> {
        return this.http.post<ApiResponseWithMessage<null>>(`${this.apiUrl}auth/logout`, {});
    }

    forgotPassword(request: ForgotPasswordRequest): Observable<ApiResponseWithMessage<null>> {
        return this.http.post<ApiResponseWithMessage<null>>(`${this.apiUrl}auth/forgotPassword`, request);
    }

    validateResetToken(request: ValidatePasswordResetTokenRequest): Observable<ApiResponseWithMessage<ValidatePasswordResetTokenResponse>> {
        return this.http.post<ApiResponseWithMessage<ValidatePasswordResetTokenResponse>>(`${this.apiUrl}auth/validateResetToken`, request);
    }

    resetPassword(request: ResetPassworRequest): Observable<ApiResponseWithMessage<null>> {
        return this.http.post<ApiResponseWithMessage<null>>(`${this.apiUrl}auth/resetPassword`, request);
    }
}