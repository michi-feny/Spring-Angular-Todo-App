package ibee.webapp.todo_app.core.service;

import ibee.webapp.todo_app.core.entity.RefreshToken;
import ibee.webapp.todo_app.core.entity.User;
import ibee.webapp.todo_app.core.exception.RefreshTokenExpiredException;
import ibee.webapp.todo_app.core.exception.RefreshTokenInvalidException;
import ibee.webapp.todo_app.core.repository.RefreshTokenRepository;
import ibee.webapp.todo_app.core.repository.UserRepository;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import ibee.webapp.todo_app.security.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXPIRATION_TIME;

    final private RefreshTokenRepository refreshTokenRepository;
    final private UserRepository userRepository;
    final private JwtService jwtService;

    @Transactional
    public RefreshToken create(AuthenticatedUser userDetails) {

        User userReference = userRepository.getReferenceById(userDetails.getId());

        refreshTokenRepository.deleteByUserId(userDetails.getId());

        RefreshToken refreshToken = new RefreshToken(
            jwtService.generateRefreshToken(userDetails),
            Instant.now().plusMillis(REFRESH_EXPIRATION_TIME),
            userReference
        );

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void validateAndFindByToken(String token) {
        try {
            if (!jwtService.isRefreshToken(token)) {
                throw new RefreshTokenInvalidException("Provided token is not a refresh token. It might be an access token.");
            }

            if (jwtService.isTokenExpired(token)) {
                refreshTokenRepository.deleteByToken(token);
                throw new RefreshTokenExpiredException();
            }

            if (!refreshTokenRepository.existsByToken(token)) {
                throw new RefreshTokenInvalidException("Refresh token is not in database (Revoked)!");
            }

        } catch (ExpiredJwtException ex) {
            refreshTokenRepository.deleteByToken(token);
            throw new RefreshTokenExpiredException(ex);
        } catch (JwtException ex) {
            throw new RefreshTokenInvalidException("Refresh token is invalid or expired.", ex);
        }
    }

    @Transactional
    public void deleteByToken(@NotNull String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
