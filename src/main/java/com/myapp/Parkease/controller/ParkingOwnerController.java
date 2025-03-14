package com.myapp.Parkease.controller;

import com.myapp.Parkease.entity.ParkingOwner;
import com.myapp.Parkease.service.ParkingOwnerService;
import com.myapp.Parkease.entity.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parking-owners")
public class ParkingOwnerController {
    private final ParkingOwnerService parkingOwnerService;
    
    @Autowired
    public ParkingOwnerController(ParkingOwnerService parkingOwnerService) {
        this.parkingOwnerService = parkingOwnerService;
    }
    
    @GetMapping
    public ResponseEntity<List<ParkingOwner>> getAllParkingOwners() {
        return ResponseEntity.ok(parkingOwnerService.getAllParkingOwners());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ParkingOwner> getParkingOwnerById(@PathVariable Long id) {
        Optional<ParkingOwner> parkingOwner = parkingOwnerService.getParkingOwnerById(id);
        return parkingOwner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ParkingOwner> createParkingOwner(@RequestBody ParkingOwner parkingOwner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingOwnerService.saveParkingOwner(parkingOwner));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ParkingOwner> updateParkingOwner(@PathVariable Long id, @RequestBody ParkingOwner parkingOwner) {
        Optional<ParkingOwner> existingOwner = parkingOwnerService.getParkingOwnerById(id);
        if (existingOwner.isPresent()) {
            parkingOwner.setId(id);
            return ResponseEntity.ok(parkingOwnerService.saveParkingOwner(parkingOwner));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingOwner(@PathVariable Long id) {
        Optional<ParkingOwner> existingOwner = parkingOwnerService.getParkingOwnerById(id);
        if (existingOwner.isPresent()) {
            parkingOwnerService.deleteParkingOwner(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/slots")
    public ResponseEntity<List<ParkingSlot>> getOwnerParkingSlots(@PathVariable Long id) {
        Optional<ParkingOwner> parkingOwner = parkingOwnerService.getParkingOwnerById(id);
        if (parkingOwner.isPresent()) {
            return ResponseEntity.ok(parkingOwner.get().getParkingSlots());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}