package com.enigmacamp.hellospring.repository;

import com.enigmacamp.hellospring.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CourseRepository extends JpaRepository<Course, String> {
    //findBy Column
    List<Course> findByTitleContains(String title);

    List<Course> findByDescriptionContains(String description);

    @Query(value = "select * from mst_course c order by c.course_id limit ?1 offset ?2",nativeQuery = true)
    List<Course> findAllCourse(Integer page, Integer pageSize);
}
