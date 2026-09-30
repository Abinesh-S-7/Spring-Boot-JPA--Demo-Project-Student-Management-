package dev.student.project.Repository;

import dev.student.project.Dtos.CourseResponseDto;
import dev.student.project.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findCourseById(Long id);

}
