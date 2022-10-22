package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.exception.EntityExistException;
import com.enigmacamp.hellospring.model.CourseType;
import com.enigmacamp.hellospring.repository.CourseTypeRepository;
import com.enigmacamp.hellospring.repository.projection.CourseTypeView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Profile("api")
public class CourseTypeServiceImpl implements CourseTypeService {
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    public CourseTypeServiceImpl(@Autowired CourseTypeRepository courseTypeRepository) {
        this.courseTypeRepository = courseTypeRepository;
    }


    @Override
    public Page<CourseType> listWithCourses(Integer page, Integer size, String direction, String sortBy) throws Exception {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of((page - 1), size, sort);
        Page<CourseType> result = courseTypeRepository.findAll(pageable);
        return result;
    }

    @Override
    public Page<CourseTypeView> list(Integer page, Integer size, String direction, String sortBy) throws Exception {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of((page - 1), size, sort);
        Page<CourseTypeView> result = courseTypeRepository.findOnlyCourse(pageable);
        return result;
    }

    @Override
    public CourseType create(CourseType courseType) throws Exception {
        try {
            CourseType newCourseType = courseTypeRepository.save(courseType);
            return newCourseType;
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistException();
        }
    }
}
