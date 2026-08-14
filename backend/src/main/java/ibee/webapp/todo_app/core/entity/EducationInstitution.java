package ibee.webapp.todo_app.core.entity;

/**
 * EducationInstitution
 */
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "education_institution")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationInstitution {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        length = 200
    )
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "address_id",
        foreignKey = @ForeignKey(
            name = "fk_education_institution_address"
        )
    )
    private Address address;


    /*  @ManyToOne
    @JoinColumn(name = "country_code")
    private Country country;*/
}
