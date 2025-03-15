package com.myapp.Parkease.controller;

import com.myapp.Parkease.entity.ParkingSlot;
import com.myapp.Parkease.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parking-slots")
public class ParkingSlotController {
    private final ParkingSlotService parkingSlotService;
    
    @Autowired
    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }
    
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllParkingSlots() {
        return ResponseEntity.ok(parkingSlotService.getAllParkingSlots());
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSlot>> getAvailableParkingSlots() {
        return ResponseEntity.ok(parkingSlotService.getAvailableParkingSlots());
    }
    
    // Replace this endpoint
    // @GetMapping("/location/{location}")
    // public ResponseEntity<List<ParkingSlot>> getParkingSlotsByLocation(@PathVariable String location) {
    //     return ResponseEntity.ok(parkingSlotService.getParkingSlotsByLocation(location));
    // }
    
    // With this one
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<ParkingSlot>> getParkingSlotsByLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(parkingSlotService.getParkingSlotsByLocationId(locationId));
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getParkingSlotById(@PathVariable Long id) {
        Optional<ParkingSlot> parkingSlot = parkingSlotService.getParkingSlotById(id);
        return parkingSlot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ParkingSlot> createParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSlotService.saveParkingSlot(parkingSlot));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateParkingSlot(@PathVariable Long id, @RequestBody ParkingSlot parkingSlot) {
        Optional<ParkingSlot> existingSlot = parkingSlotService.getParkingSlotById(id);
        if (existingSlot.isPresent()) {
            parkingSlot.setId(id);
            return ResponseEntity.ok(parkingSlotService.saveParkingSlot(parkingSlot));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<ParkingSlot> updateParkingSlotStatus(@PathVariable Long id, @RequestParam String status) {
        ParkingSlot updatedSlot = parkingSlotService.updateParkingSlotStatus(id, status);
        if (updatedSlot != null) {
            return ResponseEntity.ok(updatedSlot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSlot(@PathVariable Long id) {
        Optional<ParkingSlot> existingSlot = parkingSlotService.getParkingSlotById(id);
        if (existingSlot.isPresent()) {
            parkingSlotService.deleteParkingSlot(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ParkingSlot> addParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        ParkingSlot newSlot = parkingSlotService.saveParkingSlot(parkingSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSlot);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingSlot>> getParkingSlotsByOwner(@PathVariable Long ownerId) {
    return ResponseEntity.ok(parkingSlotService.getParkingSlotsByOwnerId(ownerId));
}
}
