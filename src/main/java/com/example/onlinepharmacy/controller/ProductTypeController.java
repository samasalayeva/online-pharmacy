package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.services.abstracts.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/types")
@RequiredArgsConstructor
public class ProductTypeController {
    private final ProductTypeService productTypeService;
    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.get(id));
    }
    @GetMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<?> getProductTypeByPharmacy(@PathVariable Long pharmacyId){
        return ResponseEntity.ok(productTypeService.getProductTypeByPharmacy(pharmacyId));
    }
    @PreAuthorize("hasRole('admin_client_role')")
    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestParam String productType, Principal principal) {
        return ResponseEntity.ok(productTypeService.save(productType, principal.getName()));
    }
    @PreAuthorize("hasRole('admin_client_role')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam String productType) {
        return ResponseEntity.ok(productTypeService.update(id, productType));
    }

    @PreAuthorize("hasRole('admin_client_role')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productTypeService.delete(id);
        return ResponseEntity.ok("Product Type successfully removed");
    }
}
