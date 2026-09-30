package dev.student.project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.student.project.models.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")

public class Student extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Email
    private String email;

    @Pattern(
            regexp ="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "Please provide Date of Birth in yyyy-mm-dd format."
    )
    private String dob;

    private double gpa;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("students")
    private Department department;


    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
        name = "student_course",
            joinColumns =@JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnoreProperties("students")
    private Set<Course> courses = new HashSet<>();

}
