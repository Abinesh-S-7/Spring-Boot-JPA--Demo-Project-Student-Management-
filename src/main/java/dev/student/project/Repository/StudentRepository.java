package dev.student.project.Repository;

import dev.student.project.models.Student;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface StudentRepository extends JpaRepository<Student,Long> {

    void deleteById(@NonNull Long id);
}
