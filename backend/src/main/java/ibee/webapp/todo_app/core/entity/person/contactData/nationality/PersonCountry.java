package ibee.webapp.todo_app.core.entity.person.contactData.nationality;

import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
    name = "person_country",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"person_id", "country_id"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCountry 
    implements PersonRelatedEntity{

    @EmbeddedId
    private PersonCountryId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("personId")
    @JoinColumn(name = "person_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("personCountryId")
    @JoinColumn(name = "person_country_id", nullable = false)
    private Country country;

    @Column(nullable = false)
    private boolean mainCountry;

}
