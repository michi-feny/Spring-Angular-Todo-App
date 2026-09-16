package ibee.webapp.todo_app.core.entity.person.workExperience;

import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.WorkExperience;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person_work_experience")
public class PersonWorkExperience 
    implements PersonRelatedEntity<PersonWorkExperienceId>{

    /* ==============================================================================
     * 1. THE COMPOSITE PRIMARY KEY
     * ==============================================================================
     * Instead of a single Long ID, we use our Embeddable class. 
     * This ensures one Person can only be linked to a specific WorkExperience once.
     */
   // @ValidId
    @EmbeddedId
    @Builder.Default
    private PersonWorkExperienceId id = new PersonWorkExperienceId();


    /* ==============================================================================
     * 2. CORE RELATIONSHIPS (MAPPED TO THE COMPOSITE KEY)
     * ==============================================================================
     */

    /*
     * LOMBOK EXCLUSION EXPLANATION:
     * If the Person entity has a List<PersonWorkExperience> (bidirectional mapping),
     * calling toString() or equals() will print the Person, which prints this object,
     * which prints the Person again... causing a StackOverflowError. We exclude it to break the loop!
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    /* 
     * @MapsId tells Hibernate: "Take the ID from this Person object, and insert it 
     * directly into the 'personId' field inside our @EmbeddedId above."
     */
    @MapsId("personId") 
    @JoinColumn(
        name = "person_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_pwe_person")
    )
    private Person person;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    /* 
     * Similarly, this takes the ID from the WorkExperience object and puts it 
     * into the 'workExperienceId' field inside the @EmbeddedId.
     */
    @MapsId("workExperienceId") 
    @JoinColumn(
        name = "work_experience_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_pwe_work_experience")
    )
    private WorkExperience workExperience;


    /* ==============================================================================
     * 3. ADDITIONAL JOIN TABLE FIELDS
     * ==============================================================================
     */

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(
        name = "is_visible", 
        nullable = false, 
        columnDefinition = "boolean default true"
    )
        /*0
                """
                boolean default true COMMENT:
                if the work Exp should be displayed,
                if it is false:
                meaningg it is part of a merged work exp Field;
                so the Date get updated, to the starting Merge exp 
                and its end Date, but the textes(like titel and description,
                change)
                """)*/
    @Builder.Default
    private boolean visible = true;

 /*   @Column(
        name = "is_military_service",
        nullable = false,
        columnDefinition = """
        boolean default false:
        meaning, that is not a Military Service record
        If it is true: this work Exp comes from an military service
        """
    )
    @Builder.Default
    private Boolean isMilitaryService = false;
*/


    /* 
     * Standalone writable column to assign which parent record this is merged into.
     */
    @Column(name = "merged_into_work_exp_id")
    private Long mergedIntoWorkExpId;

    /* ==============================================================================
     * 4. THE SELF-REFERENCING MERGE LOGIC (THE "MAGIC" COMPOSITE KEY MAPPING)
     * ==============================================================================
     */

    /*
     * LOMBOK EXCLUSION EXPLANATION:
     * This is the "upward" pointer to the parent experience. If a child tries to print
     * its parent, the parent prints its children, causing an infinite loop. Excluded!
     */
    @ToString.Exclude 
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
        value = {
            /* 
             * THE MAGIC REUSE:
             * Because our Primary Key is composite (person_id + work_experience_id), 
             * any Foreign Key pointing to it must also supply BOTH values. 
             * However, we don't want to create a redundant 'merged_into_person_id' column
             * because this merge always happens for the SAME person!
             * 
             * By setting insertable = false and updatable = false, we tell Hibernate:
             * "Do not create a new column. Just reuse the existing 'person_id' column 
             * on this row to fulfill the first half of the composite foreign key requirement."
             */
            @JoinColumn(
                name = "person_id", 
                referencedColumnName = "person_id", 
                insertable = false, 
                updatable = false
            ),
            /* 
             * This is the ONLY new column added to the database table.
             * It fulfills the second half of the composite foreign key requirement,
             * pointing to the 'work_experience_id' of the Parent record.
             */
            @JoinColumn(
                name = "merged_into_work_exp_id", 
                referencedColumnName = "work_experience_id",
                insertable = false, 
                updatable = false
            )
        },
        foreignKey = @ForeignKey(name = "fk_pwe_merged_into")
    )
    private PersonWorkExperience mergedInto;

    /*
     * LOMBOK EXCLUSION EXPLANATION:
     * This is the "downward" pointer to child experiences. If a parent tries to print
     * its children, the children print the parent, causing an infinite loop. Excluded!
     */
    @ToString.Exclude 
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "mergedInto", fetch = FetchType.LAZY)
    @Builder.Default
    private List<PersonWorkExperience> subExperiences = new ArrayList<>();
}
