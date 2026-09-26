package com.travelgo_payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.travelgo_payment_service.config.FeignConfig;
import com.travelgo_payment_service.dto.BookingResponse;

@FeignClient(
        name = "TRAVELGO-BOOKING-SERVICE",
        configuration = FeignConfig.class
)
public interface BookingClient {

	@GetMapping("/api/bookings/internal/{id}")
	BookingResponse getBookingById(@PathVariable Long id);
	}