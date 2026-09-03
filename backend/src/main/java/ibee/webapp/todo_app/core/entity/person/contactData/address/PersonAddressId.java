package ibee.webapp.todo_app.core.entity.person.contactData.address;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAddressId implements Serializable {

    @NotNull
    @Positive
    @Column(
        name = "person_id",
        nullable  = false
        )
    private Long personId;

    @NotNull
    @Positive
    @Column(name = "address_id")
    private Long addressId;
}
