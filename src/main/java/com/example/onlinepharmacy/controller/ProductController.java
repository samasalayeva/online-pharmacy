package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.ProductResponse;
import com.example.onlinepharmacy.services.abstracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<?> getProductByPharmacyId(@PathVariable Long pharmacyId){
        return ResponseEntity.ok(productService.getProductByPharmacyId(pharmacyId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.ok(productService.getProductByCategoryId(categoryId));
    }

    @GetMapping("/productType/{productTypeId}")
    public ResponseEntity<?> getProductByProductType(@PathVariable Long productTypeId){
        return ResponseEntity.ok(productService.getProductByProductType(productTypeId));
    }

    @GetMapping("/productType/category")
    public ResponseEntity<?> findByProductTypeAndCategory(@RequestParam Long productTypeId, @RequestParam Long categoryId){
        return ResponseEntity.ok(productService.findByProductTypeAndCategory(productTypeId,categoryId));

    }
}
