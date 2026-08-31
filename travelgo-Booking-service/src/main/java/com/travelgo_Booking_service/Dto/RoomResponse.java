package com.travelgo_Booking_service.Dto;

//package com.travelgo_booking_service.dto;

import java.math.BigDecimal;

public class RoomResponse {

    private Long id;
    private String roomNumber;
    private String roomType;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private Boolean available;
    private Long propertyId;
    private String propertyName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}
	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public RoomResponse(Long id, String roomNumber, String roomType, BigDecimal pricePerNight, Integer capacity,
			Boolean available, Long propertyId, String propertyName) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.pricePerNight = pricePerNight;
		this.capacity = capacity;
		this.available = available;
		this.propertyId = propertyId;
		this.propertyName = propertyName;
	}
	public RoomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    // Generate Getters and Setters
}