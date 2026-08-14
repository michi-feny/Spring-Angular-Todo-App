package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree;

import java.time.LocalDate;

import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.hardSkills.Degree;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

@Entity
@Table(
    name="person_degree"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDegree 
    implements PersonRelatedEntity{


    @EmbeddedId
    private PersonDegreeId id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("personId")
    @JoinColumn(
        name = "person_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_degree_person"
        )
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("degreeId")
    @JoinColumn(
        name = "degree_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_degree_degree"
        )
    )
    private Degree degree;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("educationInstitutionId")
    @JoinColumn(
        name = "education_institution_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_degree_education_institution"
        )
    )
    private EducationInstitution institution;

    /*
        start date of this degree
    */
    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    /*
        end Date of this degree
    */
    @PastOrPresent
    private LocalDate endDate;

    /*
        how much persent are finisched of this degree
    */
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private double progressInPercent;

}
