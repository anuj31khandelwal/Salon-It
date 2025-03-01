package org.example.repository;

import org.example.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findByBarberIdAndStartTimeBetween(Long barberId, LocalDateTime startTime, LocalDateTime endTime);

    List<Slot> findBySalonIdAndStartTimeBetweenAndIsBookedFalse(Long salonId, LocalDateTime startTime, LocalDateTime endTime);

//    @Query("SELECT s FROM Slot s WHERE s.salon.id = :salonId AND s.isBooked = false AND s.startTime >= :startTime AND s.endTime <= :endTime")
//    List<Slot> findAvailableSlotsForSalon(@Param("salonId") Long salonId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT s FROM Slot s WHERE s.barber.id = :barberId AND s.isBooked = false AND s.startTime >= :startTime AND s.endTime <= :endTime")
    List<Slot> findByBarberIdAndStartTimeBetweenAndIsBookedFalse(@Param("barberId") Long barberId, @Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);
}
