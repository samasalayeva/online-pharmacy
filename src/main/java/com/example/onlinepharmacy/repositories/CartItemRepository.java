package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
