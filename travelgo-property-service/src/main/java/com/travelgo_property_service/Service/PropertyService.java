package com.travelgo_property_service.Service;

import java.util.List;

import com.travelgo_property_service.DTO.PropertyRequest;
import com.travelgo_property_service.DTO.PropertyResponse;

public interface PropertyService {
	 PropertyResponse createProperty(PropertyRequest request);

	    List<PropertyResponse> getAllProperties();

	    PropertyResponse getPropertyById(Long id);

	    PropertyResponse updateProperty(Long id, PropertyRequest request);

	    void deleteProperty(Long id);
}
