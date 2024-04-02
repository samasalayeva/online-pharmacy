package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserUsername(String username);
    List<Order> findByPharmacyId(Long pharmacyId);
}
