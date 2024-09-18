package com.example.demo.repository;

import com.example.demo.model.Salon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
    @Query("SELECT s FROM Salon s JOIN s.services srv WHERE s.location = :location AND srv.name = :service")
    List<Salon> findByLocationAndService(String location, String service);
}
