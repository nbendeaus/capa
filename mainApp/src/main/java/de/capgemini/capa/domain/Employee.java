package de.capgemini.capa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import de.capgemini.capa.domain.enumeration.Team;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "day_rate", nullable = false)
    private Long dayRate;

    @Column(name = "short_name")
    private String shortName;

    @NotNull
    @Column(name = "shore", nullable = false)
    private String shore;

    @NotNull
    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "project_availability")
    private Double projectAvailability;

    @Column(name = "intern_project_availability")
    private Double internProjectAvailability;

    @Column(name = "form_date")
    private LocalDate formDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "capacity_pa")
    private Double capacityPA;

    @Column(name = "capacity_ipa")
    private Double capacityIPA;

    @Enumerated(EnumType.STRING)
    @Column(name = "team")
    private Team team;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDayRate() {
        return dayRate;
    }

    public Employee dayRate(Long dayRate) {
        this.dayRate = dayRate;
        return this;
    }

    public void setDayRate(Long dayRate) {
        this.dayRate = dayRate;
    }

    public String getShortName() {
        return shortName;
    }

    public Employee shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShore() {
        return shore;
    }

    public Employee shore(String shore) {
        this.shore = shore;
        return this;
    }

    public void setShore(String shore) {
        this.shore = shore;
    }

    public String getGrade() {
        return grade;
    }

    public Employee grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getProjectAvailability() {
        return projectAvailability;
    }

    public Employee projectAvailability(Double projectAvailability) {
        this.projectAvailability = projectAvailability;
        return this;
    }

    public void setProjectAvailability(Double projectAvailability) {
        this.projectAvailability = projectAvailability;
    }

    public Double getInternProjectAvailability() {
        return internProjectAvailability;
    }

    public Employee internProjectAvailability(Double internProjectAvailability) {
        this.internProjectAvailability = internProjectAvailability;
        return this;
    }

    public void setInternProjectAvailability(Double internProjectAvailability) {
        this.internProjectAvailability = internProjectAvailability;
    }

    public LocalDate getFormDate() {
        return formDate;
    }

    public Employee formDate(LocalDate formDate) {
        this.formDate = formDate;
        return this;
    }

    public void setFormDate(LocalDate formDate) {
        this.formDate = formDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Employee toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getCapacityPA() {
        return capacityPA;
    }

    public Employee capacityPA(Double capacityPA) {
        this.capacityPA = capacityPA;
        return this;
    }

    public void setCapacityPA(Double capacityPA) {
        this.capacityPA = capacityPA;
    }

    public Double getCapacityIPA() {
        return capacityIPA;
    }

    public Employee capacityIPA(Double capacityIPA) {
        this.capacityIPA = capacityIPA;
        return this;
    }

    public void setCapacityIPA(Double capacityIPA) {
        this.capacityIPA = capacityIPA;
    }

    public Team getTeam() {
        return team;
    }

    public Employee team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Location getLocation() {
        return location;
    }

    public Employee location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Project getProject() {
        return project;
    }

    public Employee project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", dayRate='" + getDayRate() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", shore='" + getShore() + "'" +
            ", grade='" + getGrade() + "'" +
            ", projectAvailability='" + getProjectAvailability() + "'" +
            ", internProjectAvailability='" + getInternProjectAvailability() + "'" +
            ", formDate='" + getFormDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", capacityPA='" + getCapacityPA() + "'" +
            ", capacityIPA='" + getCapacityIPA() + "'" +
            ", team='" + getTeam() + "'" +
            "}";
    }
}
