package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.model.response.CoursePaymentResponse;

public interface CoursePaymentService {
    CoursePaymentResponse pay(String transactionId);
}
