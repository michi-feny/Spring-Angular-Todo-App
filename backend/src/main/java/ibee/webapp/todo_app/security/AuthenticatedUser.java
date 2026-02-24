package ibee.webapp.todo_app.security;


import ibee.webapp.todo_app.core.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class AuthenticatedUser implements UserDetails {
    final private Long id;
    final private String username;
    final private String password;
    final private String name;
    final private List<GrantedAuthority> authorities;

    public AuthenticatedUser(User user) {
        this.id = user.getId();
        this.username = user.getEmail(); // Use email as username
        this.name = user.getName();
        this.password = user.getPassword();
        this.authorities =  Stream.of(
                        user.getRoles().split(",")
                )
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @NotNull
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getRoleStrings() {
        return this.authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}