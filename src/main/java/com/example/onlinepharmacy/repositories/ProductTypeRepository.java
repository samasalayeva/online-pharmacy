package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {
 List<ProductType> findByPharmacyId(Long pharmacyId);
}
