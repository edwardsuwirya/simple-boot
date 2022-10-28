package com.enigmacamp.hellospring.repository;

import com.enigmacamp.hellospring.exception.RestTemplateException;
import com.enigmacamp.hellospring.model.request.CoursePaymentRequest;
import com.enigmacamp.hellospring.model.response.CoursePaymentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "service.paymentUrl=dummy",
})
class CoursePaymentRepositoryImplTest {

    @TestConfiguration
    static class CourseServiceConfiguration {
        @MockBean
        private RestTemplate restTemplate;

        @Bean
        public CoursePaymentRepository coursePaymentRepository() {
            return new CoursePaymentRepositoryImpl(restTemplate);
        }
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CoursePaymentRepository coursePaymentRepository;

    @Test
    public void itShouldSuccessPayment() {
        CoursePaymentRequest coursePaymentRequest = new CoursePaymentRequest();
        coursePaymentRequest.setTransactionId("dummy id");
        CoursePaymentResponse paymentResponse = new CoursePaymentResponse();
        paymentResponse.setSuccess(true);
        paymentResponse.setTransactionId("dummy id");

        when(restTemplate.postForEntity(
                anyString(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity(paymentResponse, HttpStatus.OK));
        CoursePaymentResponse actualResponse = coursePaymentRepository.callPaymentApi(coursePaymentRequest);
        assertEquals(actualResponse.isSuccess(), true);
        assertEquals(actualResponse.getTransactionId(), "dummy id");
    }

    @Test
    public void itThrowErrorWhenPaymentResponseFalse() {
        CoursePaymentRequest coursePaymentRequest = new CoursePaymentRequest();
        coursePaymentRequest.setTransactionId("dummy id");
        CoursePaymentResponse paymentResponse = new CoursePaymentResponse();
        paymentResponse.setSuccess(false);
        paymentResponse.setTransactionId("dummy id");

        when(restTemplate.postForEntity(
                anyString(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity(paymentResponse, HttpStatus.OK));

        assertThrows(RestTemplateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                coursePaymentRepository.callPaymentApi(coursePaymentRequest);
            }
        });
    }

    @Test
    public void itThrowErrorWhenPaymentResponse4xxOr5xx() {
        when(restTemplate.postForEntity(
                anyString(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity(new CoursePaymentResponse(), HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(RestTemplateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                coursePaymentRepository.callPaymentApi(new CoursePaymentRequest());
            }
        });
        when(restTemplate.postForEntity(
                anyString(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity(new CoursePaymentResponse(), HttpStatus.BAD_REQUEST));
        Exception exception = assertThrows(RestTemplateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                coursePaymentRepository.callPaymentApi(new CoursePaymentRequest());
            }
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Service is Down at dummy (503)";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void itThrowErrorWhenPaymentServiceTimeout() {
        RestClientException ex = new RestClientException("Error", new SocketTimeoutException());
        when(restTemplate.postForEntity(
                anyString(), any(), any(Class.class)))
                .thenThrow(ex);

        Exception exception = assertThrows(RestTemplateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                coursePaymentRepository.callPaymentApi(new CoursePaymentRequest());
            }
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Service time out at dummy (503)";
        assertEquals(expectedMessage, actualMessage);
    }
}