package ibee.webapp.todo_app.core.entity;

import org.hibernate.annotations.Immutable;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
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

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    @PreRemove
    private void preventDeletion() {
        throw new IllegalStateException("WARNUNG: Ein Land darf niemals aus der Datenbank gelöscht werden!");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@ValidId
    private Long id;
    
    @Column(length = 2, nullable = false, unique = true)
    private String code;

    @Column(nullable = false, length = 150)
    private String name; // Added to match the database table schema

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<CountryTranslation> translations = new ArrayList<>();

    public CountryTranslation getActiveTranslation(String languageCode) {
        if (translations == null || translations.isEmpty()) return null;
        
        return translations.stream()
                .filter(t -> t.getLanguageCode().equalsIgnoreCase(languageCode))
                .findFirst()
                .orElse(translations.get(0)); // Fallback
    }

     public static Country referenceOf(Long id) {
        if (id == null) {
            return null;
        }
        Country country = new Country();
        country.id = id;
        return country;
    }
   /*  public Long getIdOfCountry(){
        return id;
    }*/
}
