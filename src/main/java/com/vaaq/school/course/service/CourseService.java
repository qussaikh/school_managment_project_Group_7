package com.vaaq.school.course.service;


import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public void saveCourse(Course courseObj) {
        courseRepository.save(courseObj);
    }

    public List<Course> getCourseDetails(Long courseId) {
        if (null != courseId) {
            return courseRepository.findAllByCourseId(courseId);
        } else {
            return courseRepository.findAll();
        }
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

    public void deleteCourse(Long CourseId) {
        courseRepository.deleteById(CourseId);
    }

    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }
}

