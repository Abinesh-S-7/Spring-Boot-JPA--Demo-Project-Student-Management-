package dev.student.project.Dtos;

public record StudentSummaryDto(
        Long id,
        String name,
        String email,
        String departmentName
) {}