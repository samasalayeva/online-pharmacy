package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Cart;
import com.example.onlinepharmacy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
