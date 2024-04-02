package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByPharmacyId(Long id);

}
