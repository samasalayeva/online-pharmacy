package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.CategoryRequest;
import com.example.onlinepharmacy.dtos.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse get(Long id);
    CategoryResponse save(CategoryRequest request);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
}
