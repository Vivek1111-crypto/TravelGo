package com.travelgo_payment_service.exception;

//package com.travelgo_payment_service.exception;

public class DuplicatePaymentException extends RuntimeException {

    public DuplicatePaymentException(String message) {
        super(message);
    }
}