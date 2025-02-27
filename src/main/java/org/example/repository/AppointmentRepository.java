package org.example.repository;

import org.example.entity.Appointments;
import org.example.entity.AvailableSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    List<Appointments> findByCustomerId(Long userId);
    List<Appointments> findByBarberId(Long barberId);
    List<Appointments> findBySalonId(Long salonId);
    List<Appointments> findByCustomer(org.example.entity.SalonUser customer);
    List<Appointments> findBySalon(org.example.entity.Salon salon);
    List<Appointments> findByBarber(org.example.entity.Barber barber);

    @Query("SELECT new org.example.entity.AvailableSlot(a.appointmentTime, a.appointmentTime) " +
            "FROM Appointments a " +
            "WHERE a.barber.id = :barberId " +
            "AND a.appointmentTime >= :startTime " +
            "AND a.appointmentTime <= :endTime " +
            "AND a.status = 'AVAILABLE'")
    List<AvailableSlot> findAvailableSlotsForBarber(@Param("barberId") Long barberId,
                                                    @Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);

    @Query("SELECT new org.example.entity.AvailableSlot(a.appointmentTime, a.appointmentTime) " +
            "FROM Appointments a " +
            "WHERE a.salon.id = :salonId " +
            "AND a.appointmentTime >= :startTime " +
            "AND a.appointmentTime <= :endTime " +
            "AND a.status = 'AVAILABLE'")
    List<AvailableSlot> findAvailableSlotsForSalon(@Param("salonId") Long salonId,
                                                   @Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);

    void deleteByAppointmentTime(LocalDateTime startTime);
}
