package com.travelgo_payment_service.exception;
//package com.travelgo_payment_service.exception;

public class UnauthorizedPaymentException extends RuntimeException {

    public UnauthorizedPaymentException(String message) {
        super(message);
    }
}