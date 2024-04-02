package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.ProductResponse;
import com.example.onlinepharmacy.models.Product;

import java.util.List;

public interface ProductService {
    ProductResponse save(ProductRequest request);
    List<ProductResponse> getAll();
    ProductResponse get(Long id);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);

    List<ProductResponse> getProductByPharmacyId(Long pharmacyId);

    List<ProductResponse> getProductByCategoryId(Long categoryId);

    List<ProductResponse> getProductByProductType(Long productTypeId);
    List<ProductResponse> findByProductTypeAndCategory(Long productTypeId, Long categoryId);

}
