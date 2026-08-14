package ibee.webapp.todo_app.core.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * Email
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "email_address",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = "email_address"
        )
    }

)//TODO: RENAME: email_address 
public class EmailAddress {

    @Id
    @GeneratedValue(
        strategy 
            = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "email_address",
        nullable = false,
       // unique = true, is at table lvl defined
        length = 320
    )
    private String emailAddress;

    

}
