package xyz.khamim.dynamodblocal.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.khamim.dynamodblocal.model.Student;
import xyz.khamim.dynamodblocal.service.DynamoDbService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentApiController {

    private final DynamoDbService dynamoDBService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return dynamoDBService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return dynamoDBService.getStudent(id);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return dynamoDBService.getAllStudents();
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable String id, @RequestBody Student student) {
        dynamoDBService.updateStudent(id, student);

        return student;
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        dynamoDBService.deleteStudent(id);
    }
}
