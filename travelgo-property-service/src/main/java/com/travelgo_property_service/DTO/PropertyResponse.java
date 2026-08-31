package com.travelgo_property_service.DTO;
import java.math.BigDecimal;

public class PropertyResponse {

    private Long id;
    private String name;
    private String description;
    private String city;
    private String state;
    private String country;
    private String address;
    private BigDecimal pricePerNight;
    private Integer maxGuests;
    private Integer bedrooms;
    private Integer bathrooms;
    private String amenities;
    private Boolean available;
	public PropertyResponse(Long id, String name, String description, String city, String state, String country,
			String address, BigDecimal pricePerNight, Integer maxGuests, Integer bedrooms, Integer bathrooms,
			String amenities, Boolean available) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.city = city;
		this.state = state;
		this.country = country;
		this.address = address;
		this.pricePerNight = pricePerNight;
		this.maxGuests = maxGuests;
		this.bedrooms = bedrooms;
		this.bathrooms = bathrooms;
		this.amenities = amenities;
		this.available = available;
	}
	
	public PropertyResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
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
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
    
    

    // Generate Getters & Setters
}