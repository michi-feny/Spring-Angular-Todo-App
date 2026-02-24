package ibee.webapp.todo_app.security.cookie;

import ibee.webapp.todo_app.core.dto.response.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CookieTokenService {
    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXPIRATION_TIME;
    @Value("${jwt.expiration.time}")
    private Long EXPIRATION_TIME;

    private static final String DOMAIN = "localhost";
    private static final String REFRESH_PATH = "/api/v1/auth/refresh";
    private static final String ACCESS_PATH = "/";

    public HttpHeaders createTokenHeaders(TokenPair tokenPair) {

        String accessToken = Objects.requireNonNull(tokenPair.accessToken(), "Access Token can't be null after successful authentication.");

        String[] parts = JwtSplitter.splitJwt(accessToken);
        String jwtHp = parts[0];
        String jwtS = parts[1];

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokenPair.refreshToken())
                .httpOnly(true)
                .secure(true)
                .maxAge(REFRESH_EXPIRATION_TIME / 1000)
                .path(REFRESH_PATH)
                .sameSite("Lax")
                .domain(DOMAIN)
                .build();

        ResponseCookie hpCookie = ResponseCookie.from("jwt_hp", jwtHp)
                .httpOnly(false)
                .secure(true)
                .maxAge(EXPIRATION_TIME / 1000)
                .path(ACCESS_PATH)
                .sameSite("Strict")
                .domain(DOMAIN)
                .build();

        ResponseCookie sCookie = ResponseCookie.from("jwt_s", jwtS)
                .httpOnly(true)
                .secure(true)
                .maxAge(EXPIRATION_TIME / 1000)
                .path(ACCESS_PATH)
                .sameSite("Strict")
                .domain(DOMAIN)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, hpCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, sCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return headers;
    }

    public HttpHeaders clearCookies() {

        final int MAX_AGE_ZERO = 0;

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .maxAge(MAX_AGE_ZERO)
                .path(REFRESH_PATH)
                .sameSite("Lax")
                .domain(DOMAIN)
                .build();

        ResponseCookie hpCookie = ResponseCookie.from("jwt_hp", "")
                .httpOnly(false)
                .secure(true)
                .maxAge(MAX_AGE_ZERO)
                .path(ACCESS_PATH)
                .sameSite("Strict")
                .domain(DOMAIN)
                .build();

        ResponseCookie sCookie = ResponseCookie.from("jwt_s", "")
                .httpOnly(true)
                .secure(true)
                .maxAge(MAX_AGE_ZERO)
                .path(ACCESS_PATH)
                .sameSite("Strict")
                .domain(DOMAIN)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, hpCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, sCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return headers;
    }
}
