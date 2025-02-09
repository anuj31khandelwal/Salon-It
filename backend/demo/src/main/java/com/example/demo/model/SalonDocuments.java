package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SalonDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon; // Assuming you have a Salon entity

    private String cosmetologyLicensePath;
    private String idProofPath;
    private String taxIdPath;
    private String bankAccountDetailsPath;
    private String serviceMenuPath;
    private String bestWorkPhotosPath;
}
