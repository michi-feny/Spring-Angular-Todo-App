package ibee.webapp.todo_app.core.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
   // @ValidId
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "legal_form")
    private String legalForm; // e.g., LLC, Inc., Ltd.

    
    //@NotNull
    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "address_id",         // 1. Creates a column named "address_id" in the "company" table
        referencedColumnName = "id", // 2. Matches exactly with the "id" field in your Address entity
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_company_address"
        )
    )
    private Address address;

   



}
