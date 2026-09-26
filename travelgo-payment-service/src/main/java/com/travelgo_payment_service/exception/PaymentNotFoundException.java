package com.travelgo_payment_service.exception;

//package com.travelgo_payment_service.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String message) {
        super(message);
    }
}