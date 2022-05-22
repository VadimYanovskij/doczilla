package com.doczilla.students.controller;

import com.doczilla.students.domain.Student;
import com.doczilla.students.repository.CommonRepository;
import com.doczilla.students.validation.StudentValidationErrorBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/student")
public class StudentController {

    private final CommonRepository<Student> repository;

    public StudentController(CommonRepository<Student> repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Student>> getStudents() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Integer id) {
        Student student = new Student();
        student.setId(id);
        repository.delete(student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Student> deleteStudent(@RequestBody Student student) {
        repository.delete(student);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(StudentValidationErrorBuilder.fromBindingErrors(errors));
        }
        repository.save(student);
        return ResponseEntity.noContent().build();
    }
}
