package com.vaaq.school.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaaq.school.course.controller.CourseController;
import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void createCourse() throws Exception {
        Course course = new Course(1L, "Java Basics", "JAVA", 10, 500.0, null);



        mockMvc.perform(post("/course/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isCreated());

        verify(courseService, times(1)).saveCourse(any(Course.class));
    }


    @Test
    void getCourse() throws Exception {
        Long courseId = 1L;
        Course course = new Course(courseId, "Java Basics", "JAVA", 10, 500.0, null);
        given(courseService.getCourseDetails(courseId)).willReturn(List.of(course));

        mockMvc.perform(get("/course/getCourse/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseId").value(courseId))
                .andExpect(jsonPath("$[0].title").value("Java Basics"));
    }

    @Test
    void getAllCourses() throws Exception {
        Course course1 = new Course(1L, "Java Basics", "JAVA", 10, 500.0, null);
        Course course2 = new Course(2L, "Advanced Java", "ADVJAVA", 15, 700.0, null);
        List<Course> allCourses = Arrays.asList(course1, course2);
        given(courseService.getAllCourse()).willReturn(allCourses);

        mockMvc.perform(get("/course/getCourses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(allCourses.size()))
                .andExpect(jsonPath("$[0].title").value("Java Basics"))
                .andExpect(jsonPath("$[1].title").value("Advanced Java"));
    }

    @Test
    void removeCourse() throws Exception {
        Long courseId = 1L;
        mockMvc.perform(delete("/course/delete/{courseId}", courseId))
                .andExpect(status().isOk());
    }
}