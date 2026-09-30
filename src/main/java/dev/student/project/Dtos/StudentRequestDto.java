package dev.student.project.Dtos;

import dev.student.project.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class StudentRequestDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "Please provide Date of Birth in yyyy-mm-dd format."
    )
    private String dob;

    private Double gpa;

    private Address address;

    private Long departmentId;

    private Set<Long> courseIds;
}