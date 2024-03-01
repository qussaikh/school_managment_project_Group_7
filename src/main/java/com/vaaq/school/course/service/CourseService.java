package com.vaaq.school.course.service;


import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

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

    public void deleteCourse(Long CourseId) {
        courseRepository.deleteById(CourseId);
    }

    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }
}

