package com.example.onlinepharmacy.repositories;

import com.example.onlinepharmacy.models.Address;
import com.example.onlinepharmacy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
