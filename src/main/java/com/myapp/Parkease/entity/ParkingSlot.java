package com.myapp.Parkease.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_slots")
@Data
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String location;
    
    @Column(name = "slot_number", nullable = false)
    private Integer slotNumber;
    
    @Column(nullable = false)
    private String status = "available"; // Default value: available
}