package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entity.Appointments;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointments,Long> {
    List<Appointments> findByCustomerId(Long userId);

    List<Appointments> findByBarberId(Long barberId);

    List<Appointments> findBySalonId(Long salonId);
}
