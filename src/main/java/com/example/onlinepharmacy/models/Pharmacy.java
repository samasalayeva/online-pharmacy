package com.example.onlinepharmacy.models;

import com.example.onlinepharmacy.dtos.request.ContactInformation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User owner;

    private String name;

    private String description;

    private boolean open;

    private String openingHours;

    private LocalDate registrationDate;

    @ManyToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PharmacyImage> pharmacyImages = new ArrayList<>();

}
