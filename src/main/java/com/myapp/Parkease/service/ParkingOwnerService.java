package com.myapp.Parkease.service;

import com.myapp.Parkease.entity.ParkingOwner;
import com.myapp.Parkease.repository.ParkingOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingOwnerService {
    private final ParkingOwnerRepository parkingOwnerRepository;
    
    @Autowired
    public ParkingOwnerService(ParkingOwnerRepository parkingOwnerRepository) {
        this.parkingOwnerRepository = parkingOwnerRepository;
    }
    
    public List<ParkingOwner> getAllParkingOwners() {
        return parkingOwnerRepository.findAll();
    }
    
    public Optional<ParkingOwner> getParkingOwnerById(Long id) {
        return parkingOwnerRepository.findById(id);
    }
    
    public ParkingOwner saveParkingOwner(ParkingOwner parkingOwner) {
        return parkingOwnerRepository.save(parkingOwner);
    }
    
    public void deleteParkingOwner(Long id) {
        parkingOwnerRepository.deleteById(id);
    }
}