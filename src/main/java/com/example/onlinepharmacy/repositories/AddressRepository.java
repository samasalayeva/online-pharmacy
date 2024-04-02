package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
