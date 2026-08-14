package ibee.webapp.todo_app.core.entity.person.contactData.address;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAddressId implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "address_id")
    private Long addressId;
}
