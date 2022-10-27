package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.model.Course;
import com.enigmacamp.hellospring.repository.CourseArrayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)

// Opsi 1
@TestPropertySource(properties = {
        "course.data.length=1",
})

//Opsi 2
//@TestPropertySource("classpath:application.properties")

//Opsi 3, Syaratnya menggunakan @SpringBootTest, berarti kita melakukan integration testing
//@ActiveProfiles("test")
public class CourseServiceArrayImplTest {

    @MockBean
    CourseArrayRepository courseRepository;

    @MockBean
    ModelMapper modelMapper;

    @TestConfiguration
    static class CourseServiceConfiguration {
        @Bean
        public CourseService courseService() {
            return new CourseServiceArrayImpl() {
            };
        }
    }

    @Autowired
    private CourseService courseService;

    @Test
    void itShouldReturnListCourse() throws Exception {
        Course course = new Course();
        course.setCourseId("1");
        course.setLink("Dummy Link");
        course.setTitle("Dummy title");
        course.setDescription("Dummy description");

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);

        when(courseRepository.getAll()).thenReturn(courseList);
        List<Course> actualCourse = courseService.list();
        assertEquals(1, actualCourse.size());


    }
}
