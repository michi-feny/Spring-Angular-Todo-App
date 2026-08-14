package ibee.webapp.todo_app.core.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "person"
)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "social_record_number",
        nullable = true, length = 10)
    private short socialRecordNumber;
    
    @Column(name = "first_name",
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            nullable = false)
    private String lastName;

    //@Temporal(TemporalType.DATE)
    @Column(
        name = "birth_date",
        nullable = false)
    private LocalDate birthDate;

    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    /*@JoinColumn(
        name = "nationality_id",
        nullable = false
    )*/
   @Builder.Default
    private List<PersonCountry> nationalitys = new ArrayList<>();

    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PersonAddress> addresses = new ArrayList<>();

    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @Builder.Default
    private List<PersonPhoneNumber> phones = new ArrayList<>();

    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<PersonEmailAddress> emails = new ArrayList<>();

    
     @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<PersonDegree> degrees = new ArrayList<>();

     @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<PersonProfessionQualification> professions = new ArrayList<>();


    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<PersonAdditionalHardSkill> additionalSkills = new ArrayList<>();

    @OneToMany(
    mappedBy = "person",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonSoftSkill> softSkills = new HashSet<>();

}
