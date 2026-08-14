package ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.PhoneNumber;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * PersonPhone
 */
@Entity
@Table(
    name = "person_phone_number",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"person_id", "phone_number_id"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonPhoneNumber 
    implements PersonRelatedEntity{


    @EmbeddedId
    private PersonPhoneNumberId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("personId")
    @JoinColumn(name = "person_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("phoneNumberId")
    @JoinColumn(name = "phone_number_id", nullable = false)
    private PhoneNumber phoneNumber;

    @Column(nullable = false)
    private boolean mainNumber;

}
