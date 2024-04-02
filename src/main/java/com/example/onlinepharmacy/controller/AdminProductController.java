package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.ProductResponse;
import com.example.onlinepharmacy.services.abstracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin_client_role')")
public class AdminProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok("Product successfully removed");
    }
}
