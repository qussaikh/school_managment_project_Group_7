package com.vaaq.school.student.service;


import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.repository.CourseRepository;
import com.vaaq.school.student.entity.Student;
import com.vaaq.school.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void saveStudent(Student empObj) {
        studentRepository.save(empObj);
    }

    public List<Student> getStudentDetails(Long studentId) {

        if (null != studentId) {
            return studentRepository.findAllByStudentId(studentId);
        } else {
            return studentRepository.findAll();
        }
    }


    public Student updateStudent(Long studentId, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        if (existingStudent != null) {
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setAge(updatedStudent.getAge());
            existingStudent.setDept(updatedStudent.getDept());
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public Student assignCourseToStudent(Long stdId, Long courseId) {
        Set<Course> courseSet = null;
        Student student = studentRepository.findById(stdId).get();
        Course course = courseRepository.findById(courseId).get();
        courseSet =  student.getCourses();
        courseSet.add(course);
        student.setCourses(courseSet);
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
