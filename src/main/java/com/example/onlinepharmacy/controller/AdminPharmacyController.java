package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.PharmacyRequest;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.services.abstracts.PharmacyService;
import com.example.onlinepharmacy.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin/pharmacies")
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin_client_role')")
public class AdminPharmacyController {
    private final PharmacyService pharmacyService;

    @PostMapping
    public ResponseEntity<?> createPharmacy(@RequestBody PharmacyRequest request,Principal principal){
        Pharmacy pharmacy = pharmacyService.createPharmacy(request, principal);
        return ResponseEntity.ok(pharmacy);
    }

    @PutMapping("/{pharmacyId}")
    public ResponseEntity<?> updatePharmacy(@RequestBody PharmacyRequest request, @PathVariable Long pharmacyId,@RequestHeader("Authorization") String accessToken){
        Pharmacy pharmacy = pharmacyService.updatePharmacy(pharmacyId,request);
        return ResponseEntity.ok(pharmacy);
    }

    @DeleteMapping("/{pharmacyId}")
    public ResponseEntity<?> deletePharmacy( @PathVariable Long pharmacyId){
       pharmacyService.deletePharmacy(pharmacyId);
        return ResponseEntity.ok("Pharmacy deleted successfully");
    }

    @PutMapping("/{pharmacyId}/status")
    public ResponseEntity<?> updatePharmacyStatus( @PathVariable Long pharmacyId){
        pharmacyService.updatePharmacyStatus(pharmacyId);
        return ResponseEntity.ok("Pharmacy updated status successfully");
    }
}
