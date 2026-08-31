package com.travelgo_property_service.DTO;

import java.math.BigDecimal;

import com.travelgo_property_service.entity.RoomType;

//import com.travelgo_property_service.Entity.RoomType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomRequest {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
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

	@NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Price is required")
    private BigDecimal pricePerNight;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotNull(message = "Availability is required")
    private Boolean available;

    @NotNull(message = "Property Id is required")
    private Long propertyId;

    public RoomRequest() {
    }

    // Generate Getters & Setters
}