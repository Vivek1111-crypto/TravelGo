package com.travelgo_Booking_service.Controller;
//package com.travelgo_booking_service.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.travelgo_Booking_service.Dto.BookingRequest;
import com.travelgo_Booking_service.Dto.BookingResponse;
import com.travelgo_Booking_service.service.BookingService;

//import com.travelgo_booking_service.dto.BookingRequest;
//import com.travelgo_booking_service.dto.BookingResponse;
//import com.travelgo_booking_service.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create Booking
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(
            @Valid @RequestBody BookingRequest request) {

        return bookingService.createBooking(request);
    }

    // Get All Bookings
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get Booking By Id
    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // Get Bookings By User
    @GetMapping("/user/{userId}")
    public List<BookingResponse> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    // Update Booking
    @PutMapping("/{id}")
    public BookingResponse updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest request) {

        return bookingService.updateBooking(id, request);
    }

    // Cancel Booking
    @DeleteMapping("/{id}")
    public String cancelBooking(@PathVariable Long id) {

        bookingService.cancelBooking(id);

        return "Booking cancelled successfully";
    }
}