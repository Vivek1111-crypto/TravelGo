package com.travelgo_property_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelgo_property_service.entity.Property;

public interface PropertyRepository extends JpaRepository<Property,Long > {

	
}
