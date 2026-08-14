package ibee.webapp.todo_app.core.entity;


import org.springframework.data.annotation.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone_number",
    uniqueConstraints = @UniqueConstraint(columnNames = "number")
)
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "phone_number",
        nullable = false,
        length = 50
    )
    private String phoneNumber;//without the Leading 0!!!

    @Column(
        name = "country_code",
        nullable = false,
        length = 4
    )
    private String countryCode; // +43

    @Transient
    private String fullNumber = countryCode+phoneNumber;

    //TODO: add international CountryCode

   
}
