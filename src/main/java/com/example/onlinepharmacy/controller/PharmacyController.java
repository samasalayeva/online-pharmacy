package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.PharmacyRequest;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.services.abstracts.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @GetMapping("/search")
    public ResponseEntity<?> searchPharmacy(@RequestParam String keyword){
        List<Pharmacy> pharmacies = pharmacyService.searchPharmacy(keyword);
        return ResponseEntity.ok(pharmacies);
    }

    @GetMapping
    public ResponseEntity<?> get(){
        List<Pharmacy> pharmacies = pharmacyService.getAllPharmacy();
        return ResponseEntity.ok(pharmacies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Pharmacy pharmacy = pharmacyService.findById(id);
        return ResponseEntity.ok(pharmacy);
    }


}
