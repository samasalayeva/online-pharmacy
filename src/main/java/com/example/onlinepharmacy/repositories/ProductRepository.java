package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.models.Product;
import com.example.onlinepharmacy.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product,Long> {
  List<Product> findByPharmacyId(Long pharmacyId);
  List<Product> findByCategory_Id(Long categoryId);

  List<Product> findByProductTypeId(Long productTypeId);
  List<Product> findByProductTypeAndCategory(ProductType productType, Category category);
}
