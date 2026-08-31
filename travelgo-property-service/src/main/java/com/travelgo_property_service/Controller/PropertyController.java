package com.travelgo_property_service.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.travelgo_property_service.DTO.PropertyRequest;
import com.travelgo_property_service.DTO.PropertyResponse;
import com.travelgo_property_service.Service.PropertyService;
import org.springframework.security.access.prepost.PreAuthorize;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/properties")
@Validated
public class PropertyController {
	 private final PropertyService propertyService;

	    public PropertyController(PropertyService propertyService) {
	        this.propertyService = propertyService;
	    }

	    // Create Property
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    @PreAuthorize("hasRole('ADMIN')")
	    public PropertyResponse createProperty(
	            @Valid @RequestBody PropertyRequest request) {
	    	 System.out.println("Request = " + request);
	    	    System.out.println("Name = " + request.getName());
	    	    System.out.println("Price = " + request.getPricePerNight());

	        return propertyService.createProperty(request);
	    }

	    // Get All Properties
	    @GetMapping
	    public List<PropertyResponse> getAllProperties() {

	        return propertyService.getAllProperties();
	    }

	    // Get Property By Id
	    @GetMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public PropertyResponse getPropertyById(
	            @PathVariable Long id) {

	        return propertyService.getPropertyById(id);
	    }

	    // Update Property
	    @PutMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public PropertyResponse updateProperty(
	            @PathVariable Long id,
	            @Valid @RequestBody PropertyRequest request) {

	        return propertyService.updateProperty(id, request);
	    }

	    // Delete Property
	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public String deleteProperty(
	            @PathVariable Long id) {

	        propertyService.deleteProperty(id);

	        return "Property deleted successfully";
	    }
}
