package com.travelgo_Booking_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelgo_Booking_service.Entity.Booking;
import com.travelgo_Booking_service.Entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomId(Long roomId);
    
	
}
