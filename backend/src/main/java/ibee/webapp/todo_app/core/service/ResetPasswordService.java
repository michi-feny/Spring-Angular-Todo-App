package ibee.webapp.todo_app.core.service;

import ibee.webapp.todo_app.core.dto.requests.ForgotPasswordRequest;
import ibee.webapp.todo_app.core.dto.requests.ResetPasswordRequest;
import ibee.webapp.todo_app.core.entity.ResetToken;
import ibee.webapp.todo_app.core.entity.User;
import ibee.webapp.todo_app.core.event.PasswordResetTokenCreatedEvent;
import ibee.webapp.todo_app.core.exception.ResetTokenExpiredException;
import ibee.webapp.todo_app.core.exception.ResetTokenInvalidException;
import ibee.webapp.todo_app.core.exception.ResetTokenNotFoundException;
import ibee.webapp.todo_app.core.repository.ResetTokenRepository;
import ibee.webapp.todo_app.core.repository.UserRepository;
import ibee.webapp.todo_app.util.TokenGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final ResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGeneratorService tokenGeneratorService;

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {

        Optional<User> user = userRepository.findByEmail(request.email());

        if(user.isEmpty()) {
            return;
        }

        resetTokenRepository.deleteByUser(user.get());

        ResetToken resetToken = new ResetToken(
                tokenGeneratorService.generateRandomToken(TokenGeneratorService.STANDARD_RESET_LENGTH),
                user.get()
        );

        resetTokenRepository.save(resetToken);

        //send reset password email!
    }

    @Transactional
    public ResetToken validateToken(String token) {

        ResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(ResetTokenNotFoundException::new);

        if(resetToken.getExpiresAt().isBefore(Instant.now())) {
            resetTokenRepository.delete(resetToken);
            throw new ResetTokenExpiredException();
        }

        return resetToken;
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        ResetToken resetToken = validateToken(request.token());

        //orm fetched so user mittels extra query
        User user = resetToken.getUser();

        if (!user.getId().equals(request.userID())) {
            resetTokenRepository.delete(resetToken);
            throw new ResetTokenInvalidException();
        }

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        resetTokenRepository.delete(resetToken);
        userRepository.save(user);
    }
}
