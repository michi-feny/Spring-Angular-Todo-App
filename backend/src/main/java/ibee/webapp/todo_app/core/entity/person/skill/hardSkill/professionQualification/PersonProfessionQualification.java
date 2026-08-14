package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification;

import java.time.LocalDate;

import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.hardSkills.ProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

/**
 * PersonProfessionQualification
 */
@Entity
@Table(name="person_profession_qualification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonProfessionQualification 
    implements PersonRelatedEntity{
    
    @EmbeddedId
    private PersonProfessionQualificationId id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @MapsId("personId")
    @JoinColumn(
            name="person_id",
            nullable=false,
            foreignKey = @ForeignKey(
            name = "fk_person_profession_person"
        )
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;


    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @MapsId("professionQualificationId")
    @JoinColumn(
        name = "profession_qualification_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_profession_qualification"
        )
    )
    private ProfessionQualification professionQualification;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("educationInstitutionId")
    @JoinColumn(
        name = "education_institution_id",
        foreignKey = @ForeignKey(
        name = "fk_person_profession_institution"
        )
    )
    private EducationInstitution educationInstitution;

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    @PastOrPresent
    private LocalDate endDate;

    private String certificateNumber;

}
