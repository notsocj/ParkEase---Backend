package com.myapp.Parkease.service;

import com.myapp.Parkease.entity.ParkingSlot;
import com.myapp.Parkease.entity.Reservation;
import com.myapp.Parkease.entity.User;
import com.myapp.Parkease.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingSlotService parkingSlotService;
    
    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ParkingSlotService parkingSlotService) {
        this.reservationRepository = reservationRepository;
        this.parkingSlotService = parkingSlotService;
    }
    
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public List<Reservation> getReservationHistoryByUser(User user) {
    return reservationRepository.findByUserAndStatusIn(user, Arrays.asList("completed", "cancelled"));
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    
    public List<Reservation> getActiveReservationsByUser(User user) {
        return reservationRepository.findByUserAndStatus(user, "active");
    }
    
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        // Update the parking slot status to "reserved"
        ParkingSlot slot = reservation.getParkingSlot();
        parkingSlotService.updateParkingSlotStatus(slot.getId(), "reserved");
        
        // Save the reservation
        return reservationRepository.save(reservation);
    }
    
    @Transactional
    public Reservation cancelReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus("canceled");
            
            // Update the parking slot status back to "available"
            ParkingSlot slot = reservation.getParkingSlot();
            parkingSlotService.updateParkingSlotStatus(slot.getId(), "available");
            
            return reservationRepository.save(reservation);
        }
        return null;
    }
    
    @Transactional
    public Reservation completeReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus("completed");
            
            // Update the parking slot status back to "available"
            ParkingSlot slot = reservation.getParkingSlot();
            parkingSlotService.updateParkingSlotStatus(slot.getId(), "available");
            
            return reservationRepository.save(reservation);
        }
        return null;
    }
}