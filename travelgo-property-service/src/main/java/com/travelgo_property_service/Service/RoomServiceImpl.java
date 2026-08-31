package com.travelgo_property_service.Service;

//package com.travelgo_property_service.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travelgo_property_service.DTO.RoomRequest;
import com.travelgo_property_service.DTO.RoomResponse;
import com.travelgo_property_service.entity.Property;
import com.travelgo_property_service.entity.Room;
import com.travelgo_property_service.repository.PropertyRepository;
import com.travelgo_property_service.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
    }
    private RoomResponse mapToResponse(Room room) {

        RoomResponse response = new RoomResponse();

        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setPricePerNight(room.getPricePerNight());
        response.setCapacity(room.getCapacity());
        response.setAvailable(room.getAvailable());

        response.setPropertyId(room.getProperty().getId());
        response.setPropertyName(room.getProperty().getName());

        return response;
    }

    @Override
    public RoomResponse createRoom(RoomRequest request) {

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() ->
                        new RuntimeException("Property not found"));

        Room room = new Room();

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setCapacity(request.getCapacity());
        room.setAvailable(request.getAvailable());
        room.setProperty(property);

        Room savedRoom = roomRepository.save(room);

        return mapToResponse(savedRoom);
    }
    @Override
    public List<RoomResponse> getAllRooms() {

        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(this::mapToResponse)
                .toList();
    }

	@Override
	public RoomResponse getRoomById(Long id) {

	    Room room = roomRepository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Room not found"));

	    return mapToResponse(room);
	}
	@Override
	public List<RoomResponse> getRoomsByProperty(Long propertyId) {

	    Property property = propertyRepository.findById(propertyId)
	            .orElseThrow(() ->
	                    new RuntimeException("Property not found"));

	    List<Room> rooms = roomRepository.findByProperty(property);

	    return rooms.stream()
	            .map(this::mapToResponse)
	            .toList();
	}

	@Override
	public RoomResponse updateRoom(Long id, RoomRequest request) {

	    // Check if room exists
	    Room room = roomRepository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Room not found"));

	    // Check if property exists
	    Property property = propertyRepository.findById(request.getPropertyId())
	            .orElseThrow(() ->
	                    new RuntimeException("Property not found"));

	    // Update fields
	    room.setRoomNumber(request.getRoomNumber());
	    room.setRoomType(request.getRoomType());
	    room.setPricePerNight(request.getPricePerNight());
	    room.setCapacity(request.getCapacity());
	    room.setAvailable(request.getAvailable());
	    room.setProperty(property);

	    // Save updated room
	    Room updatedRoom = roomRepository.save(room);

	    return mapToResponse(updatedRoom);
	}

	@Override
	public void deleteRoom(Long id) {

	    Room room = roomRepository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Room not found"));

	    roomRepository.delete(room);
	}

}