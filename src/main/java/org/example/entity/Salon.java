package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "salons")
public class Salon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private SalonUser owner;

    private String name;

    private String address;

    private String phoneNumber;

    private LocalTime openingTime;

    private LocalTime closingTime;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<Barber> barbers;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<ServiceItem> services;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments;


}
