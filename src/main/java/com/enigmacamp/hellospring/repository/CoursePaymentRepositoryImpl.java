package com.enigmacamp.hellospring.repository;

import com.enigmacamp.hellospring.exception.RestTemplateException;
import com.enigmacamp.hellospring.model.request.CoursePaymentRequest;
import com.enigmacamp.hellospring.model.response.CoursePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

@Repository
public class CoursePaymentRepositoryImpl implements CoursePaymentRepository {

    @Value("${service.paymentUrl}")
    String paymentServiceUrl;

    private RestTemplate restTemplate;

    public CoursePaymentRepositoryImpl(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CoursePaymentResponse callPaymentApi(CoursePaymentRequest paymentRequest) {
        try {
            ResponseEntity<CoursePaymentResponse> response = restTemplate.postForEntity(paymentServiceUrl, paymentRequest, CoursePaymentResponse.class);
            CoursePaymentResponse paymentResponse = response.getBody();
            if (!paymentResponse.isSuccess()) {
                throw new RestTemplateException("payment", HttpStatus.UNPROCESSABLE_ENTITY, "Failed payment");
            }
            return response.getBody();
        } catch (RestClientException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new RestTemplateException(paymentServiceUrl, HttpStatus.SERVICE_UNAVAILABLE, "Service time out");
            }
            throw new RestTemplateException(paymentServiceUrl, HttpStatus.SERVICE_UNAVAILABLE, "Service is Down");
        } catch (RestTemplateException e) {
            throw new RestTemplateException(paymentServiceUrl, HttpStatus.SERVICE_UNAVAILABLE, "Service is Down");
        }
    }
}
