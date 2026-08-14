package ibee.webapp.todo_app.core.entity.person.contactData.address;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * PersonAddress
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_address"
)
public class PersonAddress 
    implements PersonRelatedEntity{

    @EmbeddedId
    private PersonAddressId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("personId")
    @JoinColumn(
        name = "person_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_address_person"
        )
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("addressId")
    @JoinColumn(
        name = "address_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_address_address"
        )
    )
    private Address address;

    
    @Column(nullable = false)
    private boolean mainAddress;

}
