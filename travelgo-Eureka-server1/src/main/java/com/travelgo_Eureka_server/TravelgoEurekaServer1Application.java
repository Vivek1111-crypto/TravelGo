package com.travelgo_Eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TravelgoEurekaServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(TravelgoEurekaServer1Application.class, args);
	}

}
