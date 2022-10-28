package com.enigmacamp.hellospring.model.response;

public class CoursePaymentResponse {
    private String transactionId;
    private boolean success;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
