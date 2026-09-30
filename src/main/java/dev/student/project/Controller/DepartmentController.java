package dev.student.project.Controller;

import dev.student.project.Dtos.DepartmentResponseDto;
import dev.student.project.Service.DepartmentService;
import dev.student.project.models.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department)
    {
        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments()
    {
        return new ResponseEntity<>(departmentService.getAllDepartment(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
        public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id)
    {
        return new ResponseEntity<>(departmentService.getDepartmentById(id),HttpStatus.OK);
    }

}
