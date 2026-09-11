package ibee.webapp.todo_app.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_experience")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(
        name = "start_date",
        nullable = false
    )
    private LocalDate startDate;

    // endDate is often nullable (in case the person currently works there)
    @Column(
        name = "end_date"
    )
    private LocalDate endDate;

    @NotBlank
    @Column(
        name = "job_title",
        nullable = true,
        columnDefinition = "TEXT"
    )
    private String jobTitle;

    // HTML text is technically a String in Java, but we map it to "TEXT" in the DB 
    // to allow for large payloads (tags, formatting, etc.)
    @Column(
        columnDefinition = "TEXT"
    )
    private String description;

    @NotNull
    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "company_id",
        referencedColumnName = "id", // Matches the id field inside your Company entity
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_work_experience_company"
        )
    )
    private Company company;

    @NotNull
    @Column(
        name = "is_military_service",
        nullable = false
    )
    private boolean militaryService = false;
}
