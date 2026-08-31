package com.travelgo_property_service.Service;

//package com.travelgo_property_service.Service;

import java.util.List;

import com.travelgo_property_service.DTO.RoomRequest;
import com.travelgo_property_service.DTO.RoomResponse;

public interface RoomService {

    RoomResponse createRoom(RoomRequest request);

    List<RoomResponse> getAllRooms();

    RoomResponse getRoomById(Long id);

    List<RoomResponse> getRoomsByProperty(Long propertyId);

    RoomResponse updateRoom(Long id, RoomRequest request);

    void deleteRoom(Long id);
}
