package com.travelgo_payment_service.service;

import com.travelgo_payment_service.dto.PaymentRequest;
import com.travelgo_payment_service.dto.PaymentResponse;

public  interface PaymentService {
	 PaymentResponse processPayment(PaymentRequest request);

	    PaymentResponse getPaymentById(Long id);

	    PaymentResponse getPaymentByBookingId(Long bookingId);
}
