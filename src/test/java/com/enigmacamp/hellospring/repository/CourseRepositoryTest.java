package com.enigmacamp.hellospring.repository;

import com.enigmacamp.hellospring.model.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Sql(scripts = {"classpath:courses.sql"})
    void findByTitleContains() {
        List<Course> list = courseRepository.findByTitleContains("dummy title 1");
        assertEquals(1, list.size());
    }

    @Test
    @Sql(scripts = {"classpath:courses.sql"})
    void findAllPagin() {
        List<Course> list = courseRepository.findAllCourse(10, 0);
        System.out.println(list);
        assertEquals(2, list.size());
    }
}