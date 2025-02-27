package org.example.repository;

import org.example.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BarberRepository extends JpaRepository<Barber,Long> {

    @Query("SELECT b FROM Barber b WHERE b.salon.id = :salonId AND b.id NOT IN " +
            "(SELECT a.barber.id FROM Appointments a " +
            "WHERE a.appointmentTime BETWEEN :startTime AND :endTime)")
    List<Barber> findAvailableBarbersInTimeRange(Long salonId, LocalDateTime startTime, LocalDateTime endTime);

    List<Barber> findBySalonId(Long salonId);
}
