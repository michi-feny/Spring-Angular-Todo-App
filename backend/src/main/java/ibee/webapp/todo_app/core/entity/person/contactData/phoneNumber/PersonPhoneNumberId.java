package ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonPhoneNumberId implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "phone_number_id")
    private Long phoneNumberId;
}