package com.travelgo_Booking_service.service;

import org.springframework.security.access.AccessDeniedException;
//package com.travelgo_booking_service.service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
//import com.travelgo_Booking_service.Dto;
import java.time.temporal.ChronoUnit;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.travelgo_Booking_service.Client.PropertyServiceClient;
import com.travelgo_Booking_service.Dto.BookingRequest;
import com.travelgo_Booking_service.Dto.BookingResponse;
import com.travelgo_Booking_service.Dto.RoomResponse;
import com.travelgo_Booking_service.Entity.Booking;
import com.travelgo_Booking_service.Entity.BookingStatus;
import com.travelgo_Booking_service.Repository.BookingRepository;
import com.travelgo_Booking_service.exception.BadRequestException;
import com.travelgo_Booking_service.exception.ConflictException;
import com.travelgo_Booking_service.exception.ResourceNotFoundException;
//package com.travelgo_Booking_service.service;

//import com.travelgo_booking_service.dto.BookingRequest;
//import com.travelgo_booking_service.dto.BookingResponse;
//import com.travelgo_booking_service.entity.Booking;
//import com.travelgo_booking_service.entity.BookingStatus;
//import com.travelgo_booking_service.repository.BookingRepository;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
   // private final BookingRepository bookingRepository;
    private final PropertyServiceClient propertyServiceClient;

    public BookingServiceImpl(BookingRepository bookingRepository,PropertyServiceClient propertyServiceClient) {
        this.bookingRepository = bookingRepository;
        this.propertyServiceClient=propertyServiceClient;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        // 1. Validate check-in and check-out dates
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new BadRequestException(
                    "Check-out date must be after check-in date.");
        }

        // 2. Fetch room details from Property Service
        RoomResponse room =
                propertyServiceClient.getRoomById(request.getRoomId());

        // 3. Check whether room exists
        if (room == null) {
            throw new ResourceNotFoundException("Room not found.");
        }

        // 4. Check whether room belongs to selected property
        if (!room.getPropertyId().equals(request.getPropertyId())) {
            throw new BadRequestException(
                    "Room does not belong to the selected property.");
        }

        // 5. Check whether room is available
        if (!Boolean.TRUE.equals(room.getAvailable())) {
            throw new BadRequestException(
                    "Room is currently unavailable.");
        }

        // 6. Check guest capacity
        if (request.getNumberOfGuests() > room.getCapacity()) {
            throw new BadRequestException(
                    "Number of guests exceeds room capacity.");
        }

        // 7. Check date overlap
        List<Booking> existingBookings =
                bookingRepository.findByRoomId(request.getRoomId());

        for (Booking existing : existingBookings) {

            // Ignore cancelled bookings
            if (existing.getBookingStatus() == BookingStatus.CANCELLED) {
                continue;
            }

            boolean overlaps =
                    request.getCheckInDate()
                            .isBefore(existing.getCheckOutDate())
                    &&
                    request.getCheckOutDate()
                            .isAfter(existing.getCheckInDate());

            if (overlaps) {
                throw new ConflictException(
                        "Room is already booked for the selected dates.");
            }
        }

        // 8. Calculate number of nights
        long numberOfNights =
                ChronoUnit.DAYS.between(
                        request.getCheckInDate(),
                        request.getCheckOutDate());

        // 9. Calculate total price
        BigDecimal totalPrice =
                room.getPricePerNight()
                        .multiply(
                                BigDecimal.valueOf(numberOfNights));

        // 10. Create booking
        Booking booking = new Booking();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Long authenticatedUserId =
                (Long) authentication.getPrincipal();

        booking.setUserId(authenticatedUserId);
        booking.setPropertyId(request.getPropertyId());
        booking.setRoomId(request.getRoomId());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setTotalPrice(totalPrice);
        booking.setBookingStatus(BookingStatus.PENDING);

        // 11. Save booking
        Booking savedBooking =
                bookingRepository.save(booking);

        // 12. Return response
        return mapToResponse(savedBooking);
    }    
    @Override
    public List<BookingResponse> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Long authenticatedUserId =
                (Long) authentication.getPrincipal();

        if (!booking.getUserId().equals(authenticatedUserId)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You are not authorized to access this booking");
        }

        return mapToResponse(booking);
    }
    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Long authenticatedUserId =
                (Long) authentication.getPrincipal();

        if (!authenticatedUserId.equals(userId)) {
            throw new AccessDeniedException(
                    "You are not authorized to access these bookings");
        }

        return bookingRepository.findByUserId(authenticatedUserId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {

        // 1. Find existing booking
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Long authenticatedUserId =
                (Long) authentication.getPrincipal();

        if (!booking.getUserId().equals(authenticatedUserId)) {
            throw new AccessDeniedException(
                    "You are not authorized to update this booking");
        }
        // 2. Validate check-in and check-out dates
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new BadRequestException(
                    "Check-out date must be after check-in date.");
        }

        // 3. Fetch room details from Property Service
        RoomResponse room =
                propertyServiceClient.getRoomById(request.getRoomId());

        // 4. Check whether room exists
        if (room == null) {
            throw new ResourceNotFoundException("Room not found.");
        }

        // 5. Check whether room belongs to selected property
        if (!room.getPropertyId().equals(request.getPropertyId())) {
            throw new BadRequestException(
                    "Room does not belong to the selected property.");
        }

        // 6. Check whether room is available
        if (!Boolean.TRUE.equals(room.getAvailable())) {
            throw new BadRequestException(
                    "Room is currently unavailable.");
        }

        // 7. Check guest capacity
        if (request.getNumberOfGuests() > room.getCapacity()) {
            throw new BadRequestException(
                    "Number of guests exceeds room capacity.");
        }

        // 8. Check overlapping bookings
        List<Booking> existingBookings =
                bookingRepository.findByRoomId(request.getRoomId());

        for (Booking existing : existingBookings) {

            // Ignore current booking
            if (existing.getId().equals(id)) {
                continue;
            }

            // Ignore cancelled bookings
            if (existing.getBookingStatus() == BookingStatus.CANCELLED) {
                continue;
            }

            boolean overlaps =
                    request.getCheckInDate()
                            .isBefore(existing.getCheckOutDate())
                    &&
                    request.getCheckOutDate()
                            .isAfter(existing.getCheckInDate());

            if (overlaps) {
                throw new ConflictException(
                        "Room is already booked for the selected dates.");
            }
        }

        // 9. Calculate number of nights
        long nights =
                ChronoUnit.DAYS.between(
                        request.getCheckInDate(),
                        request.getCheckOutDate());

        // 10. Calculate total price
        BigDecimal totalPrice =
                room.getPricePerNight()
                        .multiply(
                                BigDecimal.valueOf(nights));

        // 11. Update booking
        booking.setUserId(request.getUserId());
        booking.setPropertyId(request.getPropertyId());
        booking.setRoomId(request.getRoomId());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setTotalPrice(totalPrice);

        // 12. Save updated booking
        Booking updatedBooking =
                bookingRepository.save(booking);

        // 13. Return response
        return mapToResponse(updatedBooking);
    }
    @Override
    public void cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Long authenticatedUserId =
                (Long) authentication.getPrincipal();

        if (!booking.getUserId().equals(authenticatedUserId)) {
            throw new AccessDeniedException(
                    "You are not authorized to cancel this booking");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }
    private BookingResponse mapToResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setUserId(booking.getUserId());
        response.setPropertyId(booking.getPropertyId());
        response.setRoomId(booking.getRoomId());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setNumberOfGuests(booking.getNumberOfGuests());
        response.setTotalPrice(booking.getTotalPrice());
        response.setBookingStatus(booking.getBookingStatus());
        response.setCreatedAt(booking.getCreatedAt());

        return response;
    }
}