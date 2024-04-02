package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    Optional<ProductImage> findById(Long id);
}
