package ibee.webapp.todo_app.controller;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.core.dto.requests.*;
import ibee.webapp.todo_app.core.service.AuthService;
import ibee.webapp.todo_app.core.service.RefreshTokenService;
import ibee.webapp.todo_app.core.service.ResetPasswordService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.core.dto.response.TokenPair;
import ibee.webapp.todo_app.core.dto.response.ValidateResetTokenResponse;
import ibee.webapp.todo_app.core.entity.ResetToken;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import ibee.webapp.todo_app.security.cookie.CookieTokenService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    @Value("${spring.application.api-url}")
    private String API_URL;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;
    private final ResetPasswordService resetPasswordService;
    private final CookieTokenService tokenCookieService;
    private final TranslationService translationService;

    @PostMapping("/signUp")
    public  ResponseEntity<@NotNull ApiSuccessResponse<Void>> addNewUser(@Valid @RequestBody SignUpRequest request) {

        authService.signUpUser(request);

        List<Link> links = List.of(
            Link.builder().rel("login").href(API_URL + "auth/authenticate").method("POST").build(),
            Link.builder().rel("forgot_password").href(API_URL + "auth/forgotPassword").method("POST").build()
        );

        return new ResponseEntity<>(
            new ApiSuccessResponse<>(
                translationService.translate("signUp.success"),
                links
            ),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/authenticate")
    public  ResponseEntity<@NotNull ApiSuccessResponse<TokenPair>> authenticate(@RequestBody @Valid AuthRequest request) {

        TokenPair response = authService.authenticate(request);

        List<Link> links = List.of(
            Link.builder().rel("refresh_token").href(API_URL + "auth/refresh").method("POST").build(),
            Link.builder().rel("logout").href(API_URL + "auth/logout").method("POST").build(),
            Link.builder().rel("todos").href(API_URL + "todos").method("GET").build(),
            Link.builder().rel("create_todo").href(API_URL + "todos").method("POST").build()
        );

        return ResponseEntity.ok()
            .headers(tokenCookieService.createTokenHeaders(response))
            .body(
                new ApiSuccessResponse<>(
                    response.clearTokens(),
                    translationService.translate("auth.success"),
                    links
                )
            );
    }

    @PostMapping("/refresh")
    public ResponseEntity<@NotNull ApiSuccessResponse<TokenPair>> refreshToken(
        @CookieValue(name = "refreshToken", required = true) String refreshToken,
        @AuthenticationPrincipal AuthenticatedUser userDetails
    ) {

        TokenPair response = authService.refreshTokens(refreshToken, userDetails);

        List<Link> links = List.of(
            Link.builder().rel("logout").href(API_URL + "auth/logout").method("POST").build(),
            Link.builder().rel("todos").href(API_URL + "todos").method("GET").build(),
            Link.builder().rel("create_todo").href(API_URL + "todos").method("POST").build()
        );

        return ResponseEntity.ok()
            .headers(tokenCookieService.createTokenHeaders(response))
            .body(
                new ApiSuccessResponse<>(
                    response.clearTokens(),
                    translationService.translate("refresh.success"),
                    links
                )
            );
    }


    @PostMapping("/logout")
    public ResponseEntity<@NotNull ApiSuccessResponse<Void>> logout(
        @CookieValue(name = "refreshToken", required = false)
        String refreshToken
    ) {

        if (refreshToken != null && !refreshToken.isEmpty()) {
            refreshTokenService.deleteByToken(refreshToken);
        }

        HttpHeaders clearHeaders = tokenCookieService.clearCookies();

        List<Link> links = List.of(
            Link.builder().rel("login").href(API_URL + "auth/authenticate").method("POST").build(),
            Link.builder().rel("signUp").href(API_URL + "auth/signUp").method("POST").build(),
            Link.builder().rel("forgot_password").href(API_URL + "auth/forgotPassword").method("POST").build()
        );

        return ResponseEntity.ok()
            .headers(clearHeaders)
            .body(
                new ApiSuccessResponse<>(
                    translationService.translate("logout.success"),
                    links
                )
            );
    }


    @PostMapping("/forgotPassword")
    public ResponseEntity<@NotNull ApiSuccessResponse<Void>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {

        resetPasswordService.forgotPassword(request);

        List<Link> links = List.of(
            Link.builder().rel("login").href(API_URL + "auth/authenticate").method("POST").build(),
            Link.builder().rel("validate_reset_token").href(API_URL + "auth/validateResetToken").method("POST").build()
        );

        return ResponseEntity.accepted()
            .body(
                new ApiSuccessResponse<>(
                    translationService.translate("forgotPassword.success"),
                    links
                )
            );
    }


    @PostMapping("/validateResetToken")
    public ResponseEntity<@NotNull ApiSuccessResponse<ValidateResetTokenResponse>> validateResetToken(@RequestBody @Valid ValidatePasswordResetRequest request) {
        ResetToken resetToken = resetPasswordService.validateToken(request.token());

        List<Link> links = List.of(
            Link.builder().rel("login").href(API_URL + "auth/authenticate").method("POST").build(),
            Link.builder().rel("reset_password").href(API_URL + "auth/resetPassword").method("POST").build()
        );

        return ResponseEntity.ok()
            .body(
                new ApiSuccessResponse<>(
                    new ValidateResetTokenResponse(resetToken.getUser().getId()),
                    translationService.translate("validateResetToken.success"),
                    links
                )
            );
    }


    @PostMapping("/resetPassword")
    public ResponseEntity<@NotNull ApiSuccessResponse<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {

        resetPasswordService.resetPassword(request);

        List<Link> links = List.of(
            Link.builder().rel("login").href(API_URL + "auth/authenticate").method("POST").build()
        );

        return ResponseEntity.ok()
            .body(
                new ApiSuccessResponse<>(
                    translationService.translate("resetPassword.success"),
                    links
                )
            );
    }
}