package ibee.webapp.todo_app.core.entity.person.contactData.emailAddress;

import ibee.webapp.todo_app.core.entity.EmailAddress;
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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * PersonEmail
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "person_email_adress",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {
            "person_id", 
            "email_id"}
    )
)
public class PersonEmailAdress 
    implements PersonRelatedEntity{

    @NotNull
    @EmbeddedId
    private PersonEmailAddressId id;

    @ManyToOne(
        fetch = FetchType.LAZY, 
        optional = false
    )
    @MapsId("personId")
    @JoinColumn(
        name = "person_id",
        nullable = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne(
        fetch = FetchType.LAZY, 
        optional = false
    )
    @MapsId("emailAddressId")
    @JoinColumn(
        name = "email_address_id", 
        nullable = false
    )
    private EmailAddress emailAddress;

    @Column(nullable = false)
    private boolean mainEmail;

}
