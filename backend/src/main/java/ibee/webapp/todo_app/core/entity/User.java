package ibee.webapp.todo_app.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String roles = "ROLE_USER";

    public User(String name, String email, String password, String roles) {
        this.name = Objects.requireNonNull(name, "Name can't be null");;
        this.email = Objects.requireNonNull(email, "E-Mail can't be null");;
        this.password = Objects.requireNonNull(password, "Password can't be null");;

        if (roles == null || roles.trim().isEmpty()) {
            this.roles = "ROLE_USER";
        } else {
            this.roles = roles.trim().toUpperCase();
        }
    }
}