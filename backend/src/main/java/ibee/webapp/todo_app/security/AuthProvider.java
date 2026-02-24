package ibee.webapp.todo_app.security;

import ibee.webapp.todo_app.core.entity.User;
import ibee.webapp.todo_app.core.exception.UserNotFoundException;
import ibee.webapp.todo_app.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthProvider implements UserDetailsService {

    private final UserRepository repository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    @NotNull
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with email " + username + "not found.", "userNotFound");
        }

        return new AuthenticatedUser(user.get());
    }
}