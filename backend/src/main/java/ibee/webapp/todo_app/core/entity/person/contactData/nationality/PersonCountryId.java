package ibee.webapp.todo_app.core.entity.person.contactData.nationality;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonCountryId implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "country_id")
    private Long countryId;
}
