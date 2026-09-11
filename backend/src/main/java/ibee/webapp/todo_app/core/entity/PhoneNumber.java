package ibee.webapp.todo_app.core.entity;


import org.springframework.data.annotation.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone_number",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_phone_number_country", 
            columnNames = {
                "phone_number", 
                "country_id",
                "country_code"
            }
        )
    }

)
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@ValidId
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

    @NotNull
    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "country_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_phone_number_country"
        )
    )
    private Country country;
    

    //TODO: add international CountryCode

   
}
