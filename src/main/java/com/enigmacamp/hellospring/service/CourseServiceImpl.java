package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.exception.EntityExistException;
import com.enigmacamp.hellospring.exception.NotFoundException;
import com.enigmacamp.hellospring.model.Course;
import com.enigmacamp.hellospring.repository.CourseRepository;
import com.enigmacamp.hellospring.repository.spec.CourseSpecification;
import com.enigmacamp.hellospring.repository.spec.SearchCriteria;
import com.enigmacamp.hellospring.util.QueryOperator;
import org.hibernate.cfg.NotYetImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("api")
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    public CourseServiceImpl(@Autowired CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<Course> list(Integer page, Integer size, String direction, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of((page - 1), size, sort);
        Page<Course> result = courseRepository.findAll(pageable);
        return result;
    }

    @Override
    public List<Course> list() throws Exception {
        throw new NotYetImplementedException();
    }

    @Override
    public Course create(Course course) {
        try {
            Course newCourse = courseRepository.save(course);
            return newCourse;
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistException();
        }
    }

    @Override
    public Course get(String id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new NotFoundException("Course not found");
        }
        return course.get();
    }

    @Override
    public List<Course> getBy(String key, String value) {
        Specification spec = new CourseSpecification(new SearchCriteria(key, QueryOperator.LIKE, value));
        List result = courseRepository.findAll(spec);
        if (result.isEmpty()) {
            throw new NotFoundException("Course not found");
        }
        return result;
    }

    @Override
    public void update(Course course, String id) {
        try {
            Course existingCourse = get(id);
            modelMapper.map(course, existingCourse);
            courseRepository.save(existingCourse);
        } catch (NotFoundException e) {
            throw new NotFoundException("Update failed because ID is not found");
        }
    }

    @Override
    public void delete(String id) throws Exception {
        try {
            Course existingCourse = get(id);
            courseRepository.delete(existingCourse);
        } catch (NotFoundException e) {
            throw new NotFoundException("Delete failed because ID is not found");
        }
    }

    @Override
    public void createBulk(List<Course> courses) {

    }
}
