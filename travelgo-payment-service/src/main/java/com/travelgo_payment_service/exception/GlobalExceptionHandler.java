package com.travelgo_payment_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFound(
            PaymentNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatePaymentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePayment(
            DuplicatePaymentException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(
                error,
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedPaymentException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedPayment(
            UnauthorizedPaymentException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(
                error,
                HttpStatus.FORBIDDEN);
    }
}