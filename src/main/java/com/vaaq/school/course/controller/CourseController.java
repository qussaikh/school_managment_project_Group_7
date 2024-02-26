package com.vaaq.school.course.controller;


import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public ResponseEntity createCourse(@RequestBody Course courseObj) {
        courseService.saveCourse(courseObj);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = {"/getCourse/{courseId}"})
    public List<Course> getCourse(@PathVariable(required = false) Long courseId) {
        return courseService.getCourseDetails(courseId);
    }

    @GetMapping(value = {"/getCourses"})
    public List<Course> getAllCourses() {
        return courseService.getAllCourse();
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity removeProject(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
