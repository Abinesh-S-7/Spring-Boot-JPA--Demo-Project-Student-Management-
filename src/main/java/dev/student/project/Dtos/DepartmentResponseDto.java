package dev.student.project.Dtos;

import java.util.List;

public record DepartmentResponseDto(
        Long id,
        String name,
        List<StudentNameIdDto> students
)
{}
