package com.travelgo_Booking_service.exception;

//package com.travelgo_Booking_service.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}