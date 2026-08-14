package ibee.webapp.todo_app.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Country
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(
        length = 2, 
        nullable = false)
    @NotBlank(message = "{country.code.required}")
    @Size(max = 2, message = "{country.code.maxSize}")
    @Pattern(
        regexp = "^[A-Za-z]{2}$",
        message = "{country.code.invalid}"
    )
    private String code;   // ISO 3166-1 Alpha-2 (AT, DE, CH, US, ...)

    @NotBlank(message = "{country.name.required}")
    @Size(max = 100, message = "{country.name.maxSize}")
    @Column(nullable = false, unique = true, length = 100)
    private String name;   // Österreich, Deutschland, ...

    @Size(max = 5, message = "{country.language.maxSize}")
    @Pattern(
        regexp = "^[A-Za-z]{2,3}(-[A-Za-z]{2})?$",
        message = "{country.language.invalid}"
    )
    @Column(length = 5)
    private String language; // de, en, fr, it, ...

    // Getter und Setter
}
