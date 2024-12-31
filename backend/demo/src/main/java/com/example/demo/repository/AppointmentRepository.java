package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.salon.id = :salonId AND a.confirmed = false")
    List<Appointment> findBySalonIdAndConfirmedFalse(Long salonId);

    List<Appointment> findBySalonId(Long salonId);

}
