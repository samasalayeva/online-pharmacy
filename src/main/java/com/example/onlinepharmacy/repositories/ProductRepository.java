package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
