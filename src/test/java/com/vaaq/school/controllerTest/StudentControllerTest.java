package com.vaaq.school.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.vaaq.school.student.controller.StudentController;
import com.vaaq.school.student.entity.Student;
import com.vaaq.school.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        doNothing().when(studentService).saveStudent(student);

        ResponseEntity<Student> response = studentController.saveStudent(student);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    public void testGetStudent() {
        Long studentId = 1L;
        List<Student> students = Arrays.asList(new Student());
        when(studentService.getStudentDetails(studentId)).thenReturn(students);

        List<Student> response = studentController.getStudent(studentId);
        assertEquals(students, response);
    }

    @Test
    public void testGetAllStudent() {
        List<Student> students = Arrays.asList(new Student());
        when(studentService.getAllStudents()).thenReturn(students);
        List<Student> response = studentController.getAllStudent();
        assertEquals(students, response);
    }

    @Test
    public void testRemoveStudent() {
        Long studentId = 1L;
        ResponseEntity expectedResponse = new ResponseEntity(HttpStatus.OK);

        ResponseEntity response = studentController.removeStudent(studentId);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testAssignCourseToStudent() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student();
        when(studentService.assignCourseToStudent(studentId, courseId)).thenReturn(student);

        Student response = studentController.assignCourseToStudent(studentId, courseId);
        assertEquals(student, response);
    }

    @Test
    public void testUpdateStudent() {
        Long studentId = 1L;
        Student updatedStudent = new Student();
        when(studentService.updateStudent(studentId, updatedStudent)).thenReturn(updatedStudent);

        ResponseEntity<Student> response = studentController.updateStudent(studentId, updatedStudent);
        assertEquals(updatedStudent, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
