package dev.student.project.Service;

import dev.student.project.Dtos.CourseResponseDto;
import dev.student.project.Dtos.StudentSummaryDto;
import dev.student.project.Repository.CourseRepository;
import dev.student.project.models.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(Course course)
    {
        return courseRepository.save(course);
    }

    public List<CourseResponseDto> getAllCourses()
    {
        return courseRepository.findAll().stream().map(this::mapToCourseResponseDto).toList();
    }

    public CourseResponseDto getCourseById(Long id)
    {
        Course course = (courseRepository.findCourseById(id)
                .orElseThrow(()-> new RuntimeException("Course Not Found")));
        return mapToCourseResponseDto(course);
    }

    private CourseResponseDto mapToCourseResponseDto(Course course) {
        List<StudentSummaryDto> studentDtos = course.getStudents().stream()
                .map(student -> new StudentSummaryDto(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getDepartment() != null ? student.getDepartment().getName() : null
                ))
                .toList();

        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getCredits(),
                studentDtos
        );
    }
}
