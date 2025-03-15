package com.myapp.Parkease.controller;

import com.myapp.Parkease.entity.Location;
import com.myapp.Parkease.entity.ParkingSlot;
import com.myapp.Parkease.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;
    
    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return location.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.saveLocation(location));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        Optional<Location> existingLocation = locationService.getLocationById(id);
        if (existingLocation.isPresent()) {
            location.setId(id);
            return ResponseEntity.ok(locationService.saveLocation(location));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        Optional<Location> existingLocation = locationService.getLocationById(id);
        if (existingLocation.isPresent()) {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/slots")
    public ResponseEntity<List<ParkingSlot>> getLocationParkingSlots(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        if (location.isPresent()) {
            return ResponseEntity.ok(location.get().getParkingSlots());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}