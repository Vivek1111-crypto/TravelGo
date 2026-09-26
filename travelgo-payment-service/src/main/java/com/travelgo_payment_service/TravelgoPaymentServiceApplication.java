package com.travelgo_payment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class TravelgoPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelgoPaymentServiceApplication.class, args);
	}

}
