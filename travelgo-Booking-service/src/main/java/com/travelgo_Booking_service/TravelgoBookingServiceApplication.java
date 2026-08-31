package com.travelgo_Booking_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class TravelgoBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelgoBookingServiceApplication.class, args);
	}

}
