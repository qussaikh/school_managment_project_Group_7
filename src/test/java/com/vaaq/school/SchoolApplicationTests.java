package com.vaaq.school;

import com.vaaq.school.course.entity.Course;
import com.vaaq.school.course.repository.CourseRepository;
import com.vaaq.school.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SchoolApplicationTests {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CourseService courseService;

	@Test
	public void testCreateCourseWithH2() {
		// Create a new Course and define its properties
		Course course = new Course();
		course.setCourseId(1L);
		course.setTitle("Sample Course");
		course.setAbbreviation("SC");
		course.setModules(10);
		course.setFee(1000);

		// Save the course using the real repository (using H2 in-memory database)
		Course savedCourse = courseRepository.save(course);

		// Assert that the saved course is not null
		assertNotNull(savedCourse);
		assertEquals("Sample Course", savedCourse.getTitle());
		assertEquals("SC", savedCourse.getAbbreviation());
		assertEquals(10, savedCourse.getModules());
		assertEquals(1000, savedCourse.getFee());

		// You can also test other behaviors of your service based on the saved course
	}

}
