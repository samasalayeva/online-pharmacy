package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.PharmacyRequest;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.User;

import java.security.Principal;
import java.util.List;

public interface PharmacyService {
    Pharmacy createPharmacy(PharmacyRequest request, Principal principal);

    Pharmacy updatePharmacy(Long pharmacyId, PharmacyRequest request);

    void deletePharmacy(Long pharmacyId);

    List<Pharmacy> getAllPharmacy();

    List<Pharmacy> searchPharmacy(String keyword);

    Pharmacy findById(Long id);

    Pharmacy findByUsername(String username);

    Pharmacy updatePharmacyStatus(Long id);


}
