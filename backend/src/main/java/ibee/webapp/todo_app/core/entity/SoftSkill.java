package ibee.webapp.todo_app.core.entity;

/**
 * SoftSkill
 */

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(
    name = "soft_skill",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_soft_skill_name",
            columnNames = "name"
        )
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoftSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        length = 100
    )
    private String name;

    @Column(length = 500)
    private String description;
}
