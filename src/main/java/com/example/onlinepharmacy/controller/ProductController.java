package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.ProductResponse;
import com.example.onlinepharmacy.services.abstracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
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
