package com.example.demo.repository;

import com.example.demo.model.Salon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Service;

import java.util.List;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
    @Query("SELECT s FROM Salon s JOIN s.services srv WHERE s.location = :location AND srv.name = :service")
    List<Salon> findByLocationAndService(String location, String service);
    List<Salon> findByNameContainingIgnoreCase(String name); // Search by salon name
    List<Salon> findByLocationContainingIgnoreCase(String location); // Search by location
//    List<Salon> findByServiceNameContainingIgnoreCase(String service); // Search by service
//    List<Salon> findByLocationAndServiceNameContainingIgnoreCase(String location, String service); // Search by both
// Search by service name using a JOIN with the services list
@Query("SELECT s FROM Salon s JOIN s.services se WHERE LOWER(se.name) LIKE LOWER(CONCAT('%', :service, '%'))")
List<Salon> findByServiceNameContainingIgnoreCase(@Param("service") String service);

    // Search by location and service name using a JOIN
    @Query("SELECT s FROM Salon s JOIN s.services se WHERE LOWER(s.location) LIKE LOWER(CONCAT('%', :location, '%')) AND LOWER(se.name) LIKE LOWER(CONCAT('%', :service, '%'))")
    List<Salon> findByLocationAndServiceNameContainingIgnoreCase(@Param("location") String location, @Param("service") String service);


}
