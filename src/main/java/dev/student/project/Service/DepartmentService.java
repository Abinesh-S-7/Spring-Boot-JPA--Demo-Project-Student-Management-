package dev.student.project.Service;

import dev.student.project.Dtos.DepartmentResponseDto;
import dev.student.project.Dtos.StudentNameIdDto;
import dev.student.project.Dtos.StudentSummaryDto;
import dev.student.project.Repository.DepartmentRepository;
import dev.student.project.models.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department createDepartment(Department department)
    {
        return departmentRepository.save(department);
    }

    public DepartmentResponseDto getDepartmentById(Long id)
    {
        Department department =  departmentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Department Not Found"));
        return mapToDepartmentResponseDto(department);
    }

    public List<DepartmentResponseDto> getAllDepartment()
    {
        return departmentRepository.findAll().stream().map(this::mapToDepartmentResponseDto).toList();
    }

    private DepartmentResponseDto mapToDepartmentResponseDto(Department department)
    {
        List<StudentNameIdDto> dtos = department.getStudents().stream()
                .map(student -> new StudentNameIdDto(
                        student.getId(),
                        student.getName()
                )).toList();

        return new DepartmentResponseDto(
                department.getId(),
                department.getName(),
                dtos
        );
    }
}
