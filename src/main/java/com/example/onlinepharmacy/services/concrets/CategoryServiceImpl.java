package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.exceptions.CategoryNotFoundException;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.User;
import com.example.onlinepharmacy.repositories.CategoryRepository;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.repositories.UserRepository;
import com.example.onlinepharmacy.services.abstracts.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final PharmacyRepository pharmacyRepository;


    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepository
                .findAll()
                .stream()
                .map(c-> mapper.map(c,CategoryResponse.class))
                .toList();
    }

    @Override
    public List<CategoryResponse> getCategoryByPharmacy(Long pharmacyId) {
        return categoryRepository
                .findByPharmacyId(pharmacyId)
                .stream()
                .map(c -> mapper.map(c, CategoryResponse.class)).toList();

    }

    public CategoryResponse get(Long id){
       Category category = categoryRepository
               .findById(id)
               .orElseThrow(
                       () -> new CategoryNotFoundException("this category not found"));
       return mapper.map(category,CategoryResponse.class);
    }

    public CategoryResponse save(String categoryName, String username){
        Pharmacy pharmacy = pharmacyRepository.findByOwnerUsername(username).orElseThrow(()
                -> new NotFoundException("Pharmacy not found"));

        Category newCategory = Category.builder().name(categoryName).pharmacy(pharmacy).build();
        return mapper.map(categoryRepository.save(newCategory),CategoryResponse.class);
    }

    public CategoryResponse update(Long id, String categoryName){
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException("this category not found"));
        if(categoryName != null){
            category.setName(categoryName);
        }
        return mapper.map(categoryRepository.save(category),CategoryResponse.class);
    }

    public void delete(Long id){
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException("this category not found"));
        categoryRepository.delete(category);
    }
}
