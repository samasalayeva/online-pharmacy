package com.example.onlinepharmacy.models;

import com.example.onlinepharmacy.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate orderDate;

    private BigDecimal totalOrderPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @JsonManagedReference
    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @Transient
    public BigDecimal getTotalOrderPrice() {
        BigDecimal sum = BigDecimal.ZERO;

        for (OrderItem orderItem : orderItems) {
            sum = sum.add(orderItem.getUnitPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        return sum;
    }
}
