package com.myapp.Parkease.controller;

import com.myapp.Parkease.entity.Reservation;
import com.myapp.Parkease.entity.User;
import com.myapp.Parkease.service.ReservationService;
import com.myapp.Parkease.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;
    
    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Reservation>> getActiveReservationsByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(reservationService.getActiveReservationsByUser(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(reservation));
    }
    
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long id) {
        Reservation canceledReservation = reservationService.cancelReservation(id);
        if (canceledReservation != null) {
            return ResponseEntity.ok(canceledReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Reservation> completeReservation(@PathVariable Long id) {
        Reservation completedReservation = reservationService.completeReservation(id);
        if (completedReservation != null) {
            return ResponseEntity.ok(completedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<Reservation>> getReservationHistoryByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(reservationService.getReservationHistoryByUser(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}