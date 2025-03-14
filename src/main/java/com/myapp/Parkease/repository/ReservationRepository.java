package com.myapp.Parkease.repository;

import com.myapp.Parkease.entity.Reservation;
import com.myapp.Parkease.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserAndStatus(User user, String status);
}
