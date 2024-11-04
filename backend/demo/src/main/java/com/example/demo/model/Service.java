package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Service name is required")
    private String name;

    @Positive(message = "Price must be greater than zero")
    private double price;

    private Boolean active = true;
    private String category;

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    @JsonBackReference
    private Salon salon;

    // Default constructor
    public Service() {}

    // Parameterized constructor
    public Service(String name, double price, Salon salon) {
        this.name = name;
        this.price = price;
        this.salon = salon;
    }

    // Getters and Setters...

    public @NotBlank(message = "Service name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Service name is required") String name) {
        this.name = name;
    }

    @Positive(message = "Price must be greater than zero")
    public double getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "Price must be greater than zero") double price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
