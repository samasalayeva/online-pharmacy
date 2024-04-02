package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
