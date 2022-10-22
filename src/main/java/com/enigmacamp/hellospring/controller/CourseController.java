package com.enigmacamp.hellospring.controller;

import com.enigmacamp.hellospring.model.Course;
import com.enigmacamp.hellospring.model.request.NewCourseRequest;
import com.enigmacamp.hellospring.model.response.PagingResponse;
import com.enigmacamp.hellospring.model.response.SuccessResponse;
import com.enigmacamp.hellospring.service.CourseService;
import com.enigmacamp.hellospring.util.validation.Uuid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Profile("api")
@RestController
@RequestMapping("/courses")
@Validated // Kalo ada pathVariable/RequestParameter validation
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllCourse(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer size,
                                       @RequestParam(defaultValue = "DESC") String direction,
                                       @RequestParam(defaultValue = "courseId") String sortBy) throws Exception {
        Page<Course> courseList = courseService.list(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>(
                courseList, "Success get course list"
        ));
    }

    // Alternatif dari @ResponseStatus di class custom exception
    // @ExceptionHandler dibuat per controller
    // Kita juga bisa membuat global error controller
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ErrorResponse> handleNoSuchElementFoundException(
//            NotFoundException exception
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponse("X01", exception.getMessage()));
//    }

    @GetMapping("/{id}")
    public ResponseEntity getCourse(@PathVariable @Uuid String id) throws Exception {
        Course result = courseService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(result, "Success get course with id"));
    }

    @GetMapping(params = {"keyword", "value"})
    @Order(1)
    public ResponseEntity getCourseBy(@RequestParam @NotBlank(message = "{invalid.keyword.required}") String keyword,
                                      @RequestParam String value) throws Exception {
        List<Course> result = courseService.getBy(keyword, value);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(
                result, "Success get course by " + keyword
        ));
    }

    @PostMapping
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseRequest request) throws Exception {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Course newCourse = modelMapper.map(request, Course.class);
        System.out.println(newCourse.toString());
        Course result = courseService.create(newCourse);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(
                        result, "Success create course"
                ));
    }

}
