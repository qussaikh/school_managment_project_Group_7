package com.vaaq.school.student.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vaaq.school.course.entity.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "STUDENT_TBL")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String name;
    private int age;
    private String dept;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "STUDENT_COURSE_TABLE",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")

    )
    //@JsonManagedReference
    private Set<Course> courses;
}
