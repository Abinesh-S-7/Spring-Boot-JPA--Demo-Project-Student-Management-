package dev.student.project.Service;

import dev.student.project.Dtos.CourseSummaryDto;
import dev.student.project.Dtos.DepartmentSummaryDto;
import dev.student.project.Dtos.StudentRequestDto;
import dev.student.project.Dtos.StudentResponseDto;
import dev.student.project.Repository.CourseRepository;
import dev.student.project.Repository.DepartmentRepository;
import dev.student.project.Repository.StudentRepository;
import dev.student.project.models.Course;
import dev.student.project.models.Department;
import dev.student.project.models.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto dto) {
        Student student = new Student();
        mapDtoToStudent(dto, student);
        Student savedStudent = studentRepository.save(student);
        return mapToResponseDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return mapToResponseDto(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        mapDtoToStudent(dto, existingStudent);
        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToResponseDto(updatedStudent);
    }

    @Transactional
    public StudentResponseDto patchStudent(Long id, StudentRequestDto patchData) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if (patchData.getName() != null) {
            existingStudent.setName(patchData.getName());
        }
        if (patchData.getEmail() != null) {
            existingStudent.setEmail(patchData.getEmail());
        }
        if (patchData.getDob() != null) {
            existingStudent.setDob(patchData.getDob());
        }
        if (patchData.getGpa() != null) {
            existingStudent.setGpa(patchData.getGpa());
        }
        if (patchData.getAddress() != null) {
            existingStudent.setAddress(patchData.getAddress());
        }
        if (patchData.getDepartmentId() != null) {
            Department department = departmentRepository.findById(patchData.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existingStudent.setDepartment(department);
        }
        if (patchData.getCourseIds() != null) {
            Set<Course> courses = fetchCourses(patchData.getCourseIds());
            existingStudent.setCourses(courses);
        }

        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToResponseDto(updatedStudent);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    // --- Helper Mappers ---

    private void mapDtoToStudent(StudentRequestDto dto, Student student) {
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDob(dto.getDob());
        student.setGpa(dto.getGpa() != null ? dto.getGpa() : 0.0);
        student.setAddress(dto.getAddress());

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + dto.getDepartmentId()));
            student.setDepartment(department);
        }

        if (dto.getCourseIds() != null && !dto.getCourseIds().isEmpty()) {
            student.setCourses(fetchCourses(dto.getCourseIds()));
        }
    }

    private Set<Course> fetchCourses(Set<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return Collections.emptySet();
        }
        return new HashSet<>(courseRepository.findAllById(courseIds));
    }

    private StudentResponseDto mapToResponseDto(Student student) {
        DepartmentSummaryDto deptDto = null;
        if (student.getDepartment() != null) {
            deptDto = new DepartmentSummaryDto(
                    student.getDepartment().getId(),
                    student.getDepartment().getName()
            );
        }

        Set<CourseSummaryDto> courseDtos = new HashSet<>();
        if (student.getCourses() != null) {
            courseDtos = student.getCourses().stream()
                    .map(c -> new CourseSummaryDto(c.getId(), c.getName(), c.getCredits()))
                    .collect(Collectors.toSet());
        }

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob(),
                student.getGpa(),
                student.getAddress(),
                deptDto,
                courseDtos
        );
    }
}
