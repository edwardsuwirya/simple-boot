package com.enigmacamp.hellospring.repository;

import com.enigmacamp.hellospring.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor {

}
