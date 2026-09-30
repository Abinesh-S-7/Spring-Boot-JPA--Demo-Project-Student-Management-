package dev.student.project.Controller;

import dev.student.project.Dtos.CourseResponseDto;
import dev.student.project.Service.CourseService;
import dev.student.project.models.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody Course course)
    {
        return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<CourseResponseDto>> getAllCourse()
    {
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id)
    {
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }
}
