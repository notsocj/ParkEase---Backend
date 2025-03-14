package com.myapp.Parkease.service;


import com.myapp.Parkease.entity.ParkingSlot;
import com.myapp.Parkease.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;
    
    @Autowired
    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }
    
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }
    
    public List<ParkingSlot> getAvailableParkingSlots() {
        return parkingSlotRepository.findByStatus("available");
    }
    
    public List<ParkingSlot> getParkingSlotsByLocation(String location) {
        return parkingSlotRepository.findByLocation(location);
    }
    
    public Optional<ParkingSlot> getParkingSlotById(Long id) {
        return parkingSlotRepository.findById(id);
    }
    
    public ParkingSlot saveParkingSlot(ParkingSlot parkingSlot) {
        return parkingSlotRepository.save(parkingSlot);
    }
    
    public void deleteParkingSlot(Long id) {
        parkingSlotRepository.deleteById(id);
    }
    
    public ParkingSlot updateParkingSlotStatus(Long id, String status) {
        Optional<ParkingSlot> optionalSlot = parkingSlotRepository.findById(id);
        if (optionalSlot.isPresent()) {
            ParkingSlot slot = optionalSlot.get();
            slot.setStatus(status);
            return parkingSlotRepository.save(slot);
        }
        return null;
    }

    public List<ParkingSlot> getParkingSlotsByOwnerId(Long ownerId) {
        return parkingSlotRepository.findByOwnerId(ownerId);
}
}