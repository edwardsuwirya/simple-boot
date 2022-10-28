package com.enigmacamp.hellospring.controller;

import com.enigmacamp.hellospring.model.response.CoursePaymentResponse;
import com.enigmacamp.hellospring.model.response.SuccessResponse;
import com.enigmacamp.hellospring.service.CoursePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course-payment")
public class CoursePaymentController {

    private CoursePaymentService coursePaymentService;

    public CoursePaymentController(@Autowired CoursePaymentService coursePaymentService) {
        this.coursePaymentService = coursePaymentService;
    }

    @GetMapping
    public ResponseEntity coursePayment(@RequestParam String transactionId) throws Exception {
        CoursePaymentResponse status = coursePaymentService.pay(transactionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(
                        status, "Success payment"
                ));
    }
}
