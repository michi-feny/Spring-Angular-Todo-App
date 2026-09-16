package ibee.webapp.todo_app.core.entity;
import java.util.Objects;

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

    public boolean hasEqualValuesAs(Company incoming) {
        if (incoming == null) return false;
        return Objects.equals(this.name, incoming.getName()) &&
               Objects.equals(this.legalForm, incoming.getLegalForm()) &&
               (this.address != null && incoming.getAddress() != null ? 
                    Objects.equals(this.address.getId(), incoming.getAddress().getId()) : 
                    Objects.equals(this.address, incoming.getAddress()));
    }

   



}
