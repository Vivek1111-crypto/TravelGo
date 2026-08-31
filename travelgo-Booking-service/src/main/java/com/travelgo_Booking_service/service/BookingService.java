package com.travelgo_Booking_service.service;
//package com.travelgo_booking_service.service;

import java.util.List;

import com.travelgo_Booking_service.Dto.BookingRequest;
import com.travelgo_Booking_service.Dto.BookingResponse;

//import com.travelgo_booking_service.dto.BookingRequest;
//import com.travelgo_booking_service.dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    List<BookingResponse> getAllBookings();

    BookingResponse getBookingById(Long id);

    List<BookingResponse> getBookingsByUser(Long userId);

    BookingResponse updateBooking(Long id, BookingRequest request);

    void cancelBooking(Long id);
}