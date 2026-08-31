package com.travelgo_property_service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travelgo_property_service.DTO.PropertyRequest;
import com.travelgo_property_service.DTO.PropertyResponse;
import com.travelgo_property_service.entity.Property;
import com.travelgo_property_service.repository.PropertyRepository;


@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PropertyResponse createProperty(PropertyRequest request) {

        Property property = new Property();

        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setCity(request.getCity());
        property.setState(request.getState());
        property.setCountry(request.getCountry());
        property.setAddress(request.getAddress());
        property.setPricePerNight(request.getPricePerNight());
        property.setMaxGuests(request.getMaxGuests());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setAmenities(request.getAmenities());
        property.setAvailable(true);

        Property savedProperty = propertyRepository.save(property);

        return mapToResponse(savedProperty);
    }

    @Override
    public List<PropertyResponse> getAllProperties() {

        return propertyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyResponse getPropertyById(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        return mapToResponse(property);
    }

    @Override
    public PropertyResponse updateProperty(Long id, PropertyRequest request) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setCity(request.getCity());
        property.setState(request.getState());
        property.setCountry(request.getCountry());
        property.setAddress(request.getAddress());
        property.setPricePerNight(request.getPricePerNight());
        property.setMaxGuests(request.getMaxGuests());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setAmenities(request.getAmenities());

        Property updatedProperty = propertyRepository.save(property);

        return mapToResponse(updatedProperty);
    }

    @Override
    public void deleteProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        propertyRepository.delete(property);
    }

    private PropertyResponse mapToResponse(Property property) {

        PropertyResponse response = new PropertyResponse();

        response.setId(property.getId());
        response.setName(property.getName());
        response.setDescription(property.getDescription());
        response.setCity(property.getCity());
        response.setState(property.getState());
        response.setCountry(property.getCountry());
        response.setAddress(property.getAddress());
        response.setPricePerNight(property.getPricePerNight());
        response.setMaxGuests(property.getMaxGuests());
        response.setBedrooms(property.getBedrooms());
        response.setBathrooms(property.getBathrooms());
        response.setAmenities(property.getAmenities());
        response.setAvailable(property.getAvailable());

        return response;
    }
}