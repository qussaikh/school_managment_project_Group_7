package com.vaaq.school.student.controller;


import com.vaaq.school.student.entity.Student;
import com.vaaq.school.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<Student> saveStudent(@RequestBody Student studentObj) {
        studentService.saveStudent(studentObj);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/getStudent/{stuId}")
    public List<Student> getStudent(@PathVariable(required = false) Long stuId) {
        return studentService.getStudentDetails(stuId);
    }

    @GetMapping("/getStudents")
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @DeleteMapping("/delete/{stuId}")
    public ResponseEntity removeStudent(@PathVariable Long stuId){
        studentService.deleteStudent(stuId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{stuId}/course/{courseId}")
    public Student assignCourseToStudent(
            @PathVariable Long stuId,
            @PathVariable Long courseId
    ){
        return studentService.assignCourseToStudent(stuId, courseId);
    }

    @PutMapping("/update/{stuId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long stuId,
            @RequestBody Student updatedStudent
    ) {
        Student student = studentService.updateStudent(stuId, updatedStudent);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
