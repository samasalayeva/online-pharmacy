package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {
    @Query("SELECT p from Pharmacy p where lower(p.name) LIKE lower(concat('%',:query, '%') ) ")
    List<Pharmacy> findBySearchQuery(String query);
    Optional<Pharmacy> findByOwnerUsername(String username);
}
