package com.example.onlinepharmacy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    private String imageId;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pharmacy_id",referencedColumnName = "id")
    private Pharmacy pharmacy;
}
