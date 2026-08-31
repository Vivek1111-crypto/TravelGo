package com.travelgo_property_service.Controller;

//package com.travelgo_property_service.Controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.travelgo_property_service.DTO.RoomRequest;
import com.travelgo_property_service.DTO.RoomResponse;
import com.travelgo_property_service.Service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Create Room
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")

    public RoomResponse createRoom(
            @Valid @RequestBody RoomRequest request) {

        return roomService.createRoom(request);
    }

    // Get All Rooms
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")

    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    // Get Room By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")

    public RoomResponse getRoomById(@PathVariable Long id) {

        return roomService.getRoomById(id);
    }

    // Get Rooms By Property
    @GetMapping("/property/{propertyId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")

    public List<RoomResponse> getRoomsByProperty(
            @PathVariable Long propertyId) {

        return roomService.getRoomsByProperty(propertyId);
    }

    // Update Room
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public RoomResponse updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {

        return roomService.updateRoom(id, request);
    }

    // Delete Room
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public String deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);

        return "Room deleted successfully";
    }
}