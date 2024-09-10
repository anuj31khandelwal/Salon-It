package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="Services")
public class Service {
        @Id @GeneratedValue
        private Long id;
        private String name;
        private double cost;
        private Long salonId;
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
        public double getCost() {
            return cost;
        }
        public void setCost(double cost) {
            this.cost = cost;
        }
        public Long getSalonId() {
            return salonId;
        }
        public void setSalonId(Long salonId) {
            this.salonId = salonId;
        }
    
        // getters and setters
        
    }   
