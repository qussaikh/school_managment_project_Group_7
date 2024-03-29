package com.vaaq.school.course.repository;


import com.vaaq.school.course.entity.Course;
import com.vaaq.school.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findAllByCourseId(long courseId);

}
