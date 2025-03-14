package com.myapp.Parkease.repository;

import com.myapp.Parkease.entity.ParkingOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingOwnerRepository extends JpaRepository<ParkingOwner, Long> {
    Optional<ParkingOwner> findByEmail(String email);
    Optional<ParkingOwner> findByContact(String contact);
}