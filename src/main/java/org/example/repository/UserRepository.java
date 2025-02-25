package org.example.repository;

import org.example.entity.SalonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SalonUser,Long>{
    Optional<SalonUser> findByEmail(String email);
    Optional<SalonUser> findByPhoneNumber(String phoneNumber);
}
