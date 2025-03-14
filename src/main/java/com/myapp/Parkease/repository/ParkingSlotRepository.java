package com.myapp.Parkease.repository;

import com.myapp.Parkease.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findByStatus(String status);
    List<ParkingSlot> findByLocation(String location);
}
