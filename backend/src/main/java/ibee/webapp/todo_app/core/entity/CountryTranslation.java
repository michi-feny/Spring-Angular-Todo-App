package ibee.webapp.todo_app.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "country_translation")
public class CountryTranslation {

    public CountryTranslation(Long id, String languageCode, String name, Country country) {
        this.id = id;
        this.languageCode = languageCode;
        this.name = name;
        this.country = country;
    }

    @PreRemove
    private void preventDeletion() {
        throw new IllegalStateException("WARNUNG: Ein Land darf niemals aus der Datenbank gelöscht werden!");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "country_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_country_translation_country")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Country country;

    
}
