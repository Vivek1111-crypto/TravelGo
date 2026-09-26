package com.travelgo_payment_service.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.travelgo_payment_service.client.BookingClient;
import com.travelgo_payment_service.dto.BookingResponse;
import com.travelgo_payment_service.dto.PaymentRequest;
import com.travelgo_payment_service.dto.PaymentResponse;
import com.travelgo_payment_service.entity.Payment;
import com.travelgo_payment_service.entity.PaymentStatus;
import com.travelgo_payment_service.exception.DuplicatePaymentException;
import com.travelgo_payment_service.exception.ErrorResponse;
import com.travelgo_payment_service.exception.PaymentNotFoundException;
import com.travelgo_payment_service.exception.UnauthorizedPaymentException;
import com.travelgo_payment_service.repository.PaymentRepository;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.travelgo_payment_service.exception.UnauthorizedPaymentException;
@Service
public class PaymentServiceImpl implements PaymentService{

	private final PaymentRepository paymentRepository;
	private final BookingClient bookingClient;
	 public PaymentServiceImpl(
	            PaymentRepository paymentRepository,BookingClient bookingClient) {

	        this.paymentRepository = paymentRepository;
	        this.bookingClient=bookingClient;
	    }

	 @Override
	 public PaymentResponse processPayment(PaymentRequest request) {

	     // 1. Get logged-in user
	     Authentication authentication =
	             SecurityContextHolder.getContext().getAuthentication();

	     if (authentication == null ||
	             !(authentication.getPrincipal() instanceof Long)) {

	         throw new UnauthorizedPaymentException(
	                 "User authentication is required");
	     }

	     Long userId = (Long) authentication.getPrincipal();

	     // 2. Get booking from Booking Service
	     BookingResponse booking =
	             bookingClient.getBookingById(request.getBookingId());

	     // 3. Verify booking belongs to logged-in user
	     if (!booking.getUserId().equals(userId)) {

	         throw new UnauthorizedPaymentException(
	                 "You are not authorized to pay for this booking");
	     }

	     // 4. Check duplicate payment
	     if (paymentRepository.existsByBookingId(request.getBookingId())) {

	         throw new DuplicatePaymentException(
	                 "Payment already exists for this booking");
	     }

	     // 5. Verify payment amount
	     if (request.getAmount().compareTo(booking.getTotalPrice()) != 0) {

	         throw new RuntimeException(
	                 "Payment amount does not match booking amount");
	     }

	     // 6. Create payment
	     Payment payment = new Payment();

	     payment.setBookingId(request.getBookingId());
	     payment.setUserId(userId);
	     payment.setAmount(request.getAmount());
	     payment.setPaymentStatus(PaymentStatus.PENDING);
	     payment.setCreatedAt(LocalDateTime.now());

	     Payment savedPayment =
	             paymentRepository.save(payment);

	     // 7. Temporary payment simulation
	     savedPayment.setPaymentStatus(PaymentStatus.SUCCESS);

	     Payment updatedPayment =
	             paymentRepository.save(savedPayment);

	     return mapToResponse(updatedPayment);
	 }
	 
	 @Override
	public PaymentResponse getPaymentById(Long id) {

	    Payment payment = paymentRepository.findById(id)
	            .orElseThrow(() ->
	                    new PaymentNotFoundException("Payment not found"));

	    return mapToResponse(payment);
	}
	@Override
	public PaymentResponse getPaymentByBookingId(Long bookingId) {
		// TODO Auto-generated method stub
		Payment payment =
                paymentRepository
                        .findByBookingId(bookingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        return mapToResponse(payment);
	}
		
	private PaymentResponse mapToResponse(
            Payment payment) {

        PaymentResponse response =
                new PaymentResponse();

        response.setId(payment.getId());
        response.setBookingId(
                payment.getBookingId());
        response.setUserId(
                payment.getUserId());
        response.setAmount(
                payment.getAmount());
        response.setPaymentStatus(
                payment.getPaymentStatus());
        response.setCreatedAt(
                payment.getCreatedAt());

        return response;
    }

}
