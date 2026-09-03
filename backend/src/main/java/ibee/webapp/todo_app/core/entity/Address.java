package ibee.webapp.todo_app.core.entity;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(
    name = "address",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_address_physical_location", 
            columnNames = {"street", "house_number", "zip_code", "city", "country_id"}
        )
    }
)

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @ValidId
    private Long id;

    @Column(
        nullable = false,
        length = 200
    )
 /*    @NotBlank
    @Size(max = 200)*/
    private String street;

 /*   @NotBlank
    @Size(max = 20)*/
    @Column(
        name = "house_number",
        nullable = false,
        length = 20
    )
    private String houseNumber;

   /*  @NotBlank
    @Size(max = 20)*/
    @Column(
        name = "zip_code",
        nullable = false,
        length = 20
    )
    private String zipCode;

   /* @NotBlank
    @Size(max = 100)*/
    @Column(
        nullable = false,
        length = 100
    )
    private String city;


    @NotNull
    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "country_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_address_country"
        )
    )
    private Country country;
/* 
    @OneToMany(
        mappedBy = "address",
        fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<PersonAddress> persons =
        new ArrayList<>();
*/

    
}
