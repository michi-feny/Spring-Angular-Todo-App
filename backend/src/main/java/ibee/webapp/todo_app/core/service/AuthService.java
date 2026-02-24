package ibee.webapp.todo_app.core.service;

import ibee.webapp.todo_app.core.dto.requests.AuthRequest;
import ibee.webapp.todo_app.core.dto.response.TokenPair;
import ibee.webapp.todo_app.core.dto.requests.SignUpRequest;
import ibee.webapp.todo_app.core.entity.RefreshToken;
import ibee.webapp.todo_app.core.entity.User;
import ibee.webapp.todo_app.core.exception.UserAlreadyExists;
import ibee.webapp.todo_app.core.exception.UserNotFoundException;
import ibee.webapp.todo_app.core.repository.UserRepository;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import ibee.webapp.todo_app.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUpUser(SignUpRequest request) throws UserAlreadyExists  {

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExists(request.email(), "email");
        }

         User user = new User(
            request.name(),
            request.email(),
            passwordEncoder.encode(request.password()),
            request.role()
         );

        userRepository.save(user);
    }

    @Transactional
    public TokenPair authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();

        return createNewTokens(userDetails);
    }

    @Transactional
    public TokenPair refreshTokens(String oldRefreshToken, @Nullable AuthenticatedUser userDetails) {
        refreshTokenService.validateAndFindByToken(oldRefreshToken);

        AuthenticatedUser userDetailsToUse;

        if(userDetails != null) {
            userDetailsToUse = userDetails;
        } else {
            Long userId = jwtService.extractUserId(oldRefreshToken);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found, but referenced by a valid Refresh Token.", "user.notFound"));
            userDetailsToUse = new AuthenticatedUser(user);
        }

        return createNewTokens(userDetailsToUse);
    }

    private TokenPair createNewTokens(AuthenticatedUser user) {

        RefreshToken refreshToken = refreshTokenService.create(user);
        String accessToken = jwtService.generateAccessToken(user);

        return new TokenPair(
                accessToken,
                refreshToken.getToken(),
                jwtService.extractExpiration(refreshToken.getToken()),
                jwtService.extractExpiration(accessToken)
        );
    }
}
