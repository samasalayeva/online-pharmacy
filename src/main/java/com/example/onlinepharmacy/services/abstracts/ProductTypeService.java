package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.dtos.response.ProductTypeResponse;

import java.util.List;

public interface ProductTypeService {
    List<ProductTypeResponse> getAll();
    List<ProductTypeResponse> getProductTypeByPharmacy(Long pharmacyId);
    ProductTypeResponse get(Long id);
    ProductTypeResponse save(String productType, String username);
    ProductTypeResponse update(Long id, String productType);
    void delete(Long id);
}
