package dev.student.project.Dtos;


import dev.student.project.models.Address;
import java.util.Set;

public record StudentResponseDto(
        Long id,
        String name,
        String email,
        String dob,
        Double gpa,
        Address address,
        DepartmentSummaryDto department,
        Set<CourseSummaryDto> courses
) {}