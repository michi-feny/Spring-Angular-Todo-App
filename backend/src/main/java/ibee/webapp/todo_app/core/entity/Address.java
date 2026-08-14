package ibee.webapp.todo_app.core.entity;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
