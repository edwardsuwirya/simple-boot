package com.enigmacamp.hellospring.controller;

import com.enigmacamp.hellospring.model.CourseType;
import com.enigmacamp.hellospring.model.request.NewCourseTypeRequest;
import com.enigmacamp.hellospring.model.response.PagingResponse;
import com.enigmacamp.hellospring.model.response.SuccessResponse;
import com.enigmacamp.hellospring.repository.projection.CourseTypeView;
import com.enigmacamp.hellospring.service.CourseTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Profile("api")
@RestController
@RequestMapping("/course-types")
@Validated // Kalo ada pathVariable/RequestParameter validation
public class CourseTypeController {
    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllCourseType(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "5") Integer size,
                                           @RequestParam(defaultValue = "DESC") String direction,
                                           @RequestParam(defaultValue = "courseTypeId") String sortBy) throws Exception {
        Page<CourseTypeView> courseTypeList = courseTypeService.list(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>(
                courseTypeList, "Success get course type list"
        ));
    }

    @GetMapping("/course-list")
    public ResponseEntity getAllCourseTypeWithCourseList(@RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "5") Integer size,
                                                         @RequestParam(defaultValue = "DESC") String direction,
                                                         @RequestParam(defaultValue = "courseTypeId") String sortBy) throws Exception {
        Page<CourseType> courseTypeList = courseTypeService.listWithCourses(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>(
                courseTypeList, "Success get course type list"
        ));
    }

    @PostMapping
    public ResponseEntity createCourseType(@Valid @RequestBody NewCourseTypeRequest request) throws Exception {
        CourseType newCourseType = modelMapper.map(request, CourseType.class);
        CourseType result = courseTypeService.create(newCourseType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(
                        result, "Success create course type"
                ));
    }

}
