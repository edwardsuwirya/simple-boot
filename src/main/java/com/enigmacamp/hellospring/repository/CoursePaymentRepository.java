package com.enigmacamp.hellospring.repository;

import com.enigmacamp.hellospring.model.request.CoursePaymentRequest;
import com.enigmacamp.hellospring.model.response.CoursePaymentResponse;

public interface CoursePaymentRepository {
    CoursePaymentResponse callPaymentApi(CoursePaymentRequest coursePaymentRequest);
}
