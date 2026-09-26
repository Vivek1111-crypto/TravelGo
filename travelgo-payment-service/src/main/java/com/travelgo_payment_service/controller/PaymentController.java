package com.travelgo_payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.travelgo_payment_service.dto.PaymentRequest;
import com.travelgo_payment_service.dto.PaymentResponse;
import com.travelgo_payment_service.service.PaymentService;

import jakarta.validation.Valid;

@RestController


@RequestMapping("/api/payments")

public class PaymentController {
	private final PaymentService paymentService;
	 public PaymentController(PaymentService paymentService) {
	        this.paymentService = paymentService;
	    }
	 @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public PaymentResponse processPayment(
	            @Valid @RequestBody PaymentRequest request) {

	        return paymentService.processPayment(request);
	    }

	 @GetMapping("/{id}")
	    public PaymentResponse getPaymentById(
	            @PathVariable Long id) {

	        return paymentService.getPaymentById(id);
	    }

	    @GetMapping("/booking/{bookingId}")
	    public PaymentResponse getPaymentByBookingId(
	            @PathVariable Long bookingId) {

	        return paymentService.getPaymentByBookingId(bookingId);
	    }
	 
}
