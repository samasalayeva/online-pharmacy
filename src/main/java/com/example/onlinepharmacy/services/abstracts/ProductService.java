package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse save(ProductRequest request);
    List<ProductResponse> getAll();
    ProductResponse get(Long id);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
}
