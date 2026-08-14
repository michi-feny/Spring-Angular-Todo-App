package ibee.webapp.todo_app.core.entity.person.contactData.emailAddress;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonEmailAddressId implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "email_address_id")
    private Long emailAddressId;
}
