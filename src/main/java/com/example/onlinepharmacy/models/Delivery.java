package com.example.onlinepharmacy.models;

import com.example.onlinepharmacy.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @OneToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;


}
