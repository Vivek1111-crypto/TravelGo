package com.travelgo_Booking_service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.travelgo_Booking_service.Dto.RoomResponse;
import com.travelgo_Booking_service.config.FeignClientConfig;

@FeignClient(
        name = "travelgo-property-service",
        configuration = FeignClientConfig.class
)
public interface PropertyServiceClient {

    @GetMapping("/api/rooms/{id}")
    RoomResponse getRoomById(@PathVariable Long id);

}