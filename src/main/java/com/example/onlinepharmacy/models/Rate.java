package com.example.onlinepharmacy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.keycloak.admin.client.resource.UserResource;

import java.time.LocalDate;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    Product product;


    @CreationTimestamp
    private LocalDate creationTime;

    @UpdateTimestamp
    private LocalDate lastUpdate;
}
