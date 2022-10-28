package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.model.request.CoursePaymentRequest;
import com.enigmacamp.hellospring.model.response.CoursePaymentResponse;
import com.enigmacamp.hellospring.repository.CoursePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursePaymentServiceImpl implements CoursePaymentService {

    private CoursePaymentRepository coursePaymentRepository;

    public CoursePaymentServiceImpl(@Autowired CoursePaymentRepository coursePaymentRepository) {
        this.coursePaymentRepository = coursePaymentRepository;
    }

    @Override
    public CoursePaymentResponse pay(String transactionId) {
        CoursePaymentRequest coursePaymentRequest = new CoursePaymentRequest();
        coursePaymentRequest.setTransactionId(transactionId);
        return coursePaymentRepository.callPaymentApi(coursePaymentRequest);
    }
}
