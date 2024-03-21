package com.vaaq.school.course.service;


import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.repository.CourseRepository;
import com.vaaq.school.student.entity.Student;
import com.vaaq.school.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void saveCourse(Course courseObj) {
        courseRepository.save(courseObj);
    }

    public Course getCourseDetails(Long courseId) {
       return courseRepository.findAllByCourseId(courseId);
    }

    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId).orElse(null);
        if (existingCourse != null) {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setAbbreviation(updatedCourse.getAbbreviation());
            existingCourse.setModules(updatedCourse.getModules());
            existingCourse.setFee(updatedCourse.getFee());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            if (course.getStudents().isEmpty()) {
                courseRepository.deleteById(courseId);
            } else {
                throw new RuntimeException("Kan inte ta bort kursen eftersom det finns kopplade studenter.");
            }
        }
    }



    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }
}

