package ibee.webapp.todo_app.core.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.ParamDef;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "country")
public class Country {

    public Country(String code) {
        this.code = code;
    }
    
    @PreRemove
    private void preventDeletion() {
        throw new IllegalStateException("WARNUNG: Ein Land darf niemals aus der Datenbank gelöscht werden!");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 2, nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<CountryTranslation> translations = new ArrayList<>();

    public CountryTranslation getActiveTranslation(String languageCode) {
        if (translations == null || translations.isEmpty()) return null;
        
        return translations.stream()
                .filter(t -> t.getLanguageCode().equalsIgnoreCase(languageCode))
                .findFirst()
                .orElse(translations.get(0)); // Fallback
    }
}
