package com.travelgo_property_service.DTO;

//package com.travelgo.property.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PropertyRequest {

    @NotBlank(message = "Property name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    public String getName() {
		return name;
	}

	public PropertyRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Integer getMaxGuests() {
		return maxGuests;
	}

	public void setMaxGuests(Integer maxGuests) {
		this.maxGuests = maxGuests;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Integer getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(Integer bathrooms) {
		this.bathrooms = bathrooms;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	@NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Price is required")
    private BigDecimal pricePerNight;

    @NotNull(message = "Maximum guests is required")
    private Integer maxGuests;

    @NotNull(message = "Bedrooms are required")
    private Integer bedrooms;

    @NotNull(message = "Bathrooms are required")
    private Integer bathrooms;

    private String amenities;

    // Generate Getters & Setters
}