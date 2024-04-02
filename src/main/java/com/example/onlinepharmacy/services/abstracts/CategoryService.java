package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.response.CategoryResponse;

import java.security.Principal;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    List<CategoryResponse> getCategoryByPharmacy(Long pharmacyId);
    CategoryResponse get(Long id);
    CategoryResponse save(String categoryName, String username);
    CategoryResponse update(Long id, String categoryName);
    void delete(Long id);
}
