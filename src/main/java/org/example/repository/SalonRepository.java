package org.example.repository;

import org.example.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon,Long> {
    @Query("SELECT s FROM Salon s WHERE s.address LIKE %:location")
    List<Salon> findSalonsByLocation(String location);
}
