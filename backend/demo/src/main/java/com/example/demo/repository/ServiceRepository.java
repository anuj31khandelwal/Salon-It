package com.example.demo.repository;

import com.example.demo.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
//    List<Service> findBySalonId(Long salonId);
    @Query("SELECT s FROM Service s WHERE s.salon.id = :salonId AND s.active = true ORDER BY s.category, s.name")
    List<Service> findBySalonIdAndActiveTrue(@Param("salonId") Long salonId);
}
