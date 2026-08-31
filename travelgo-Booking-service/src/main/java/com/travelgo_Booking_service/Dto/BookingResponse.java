package com.travelgo_Booking_service.Dto;

//package com.travelgo_booking_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.travelgo_Booking_service.Entity.BookingStatus;

//import com.travelgo_booking_service.entity.BookingStatus;

public class BookingResponse {

    private Long id;
    private Long userId;
    private Long propertyId;
    private Long roomId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Integer numberOfGuests;

    private BigDecimal totalPrice;

    private BookingStatus bookingStatus;

    private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public BookingResponse(Long id, Long userId, Long propertyId, Long roomId, LocalDate checkInDate,
			LocalDate checkOutDate, Integer numberOfGuests, BigDecimal totalPrice, BookingStatus bookingStatus,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.propertyId = propertyId;
		this.roomId = roomId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numberOfGuests = numberOfGuests;
		this.totalPrice = totalPrice;
		this.bookingStatus = bookingStatus;
		this.createdAt = createdAt;
	}

	public BookingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

    // Generate Getters and Setters
}