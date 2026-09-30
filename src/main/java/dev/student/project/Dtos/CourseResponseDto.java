package dev.student.project.Dtos;

import java.util.List;

public record CourseResponseDto(
        Long id,
        String name,
        Integer credits,
        List<StudentSummaryDto> students
) {}
