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
    
    // Remove the location String field
    // @Column(nullable = false)
    // private String location;
    
    // Add ManyToOne relationship to Location
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    
    @Column(name = "slot_number", nullable = false)
    private Integer slotNumber;
    
    @Column(nullable = false)
    private String status = "available"; // Default value: available

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private ParkingOwner owner;
}