package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;  // e.g., "Haircut", "Shaving", etc.

    // Constructors, Getters, and Setters
    public Salon() {}
    public Salon(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    // Getters and Setters
    
}
