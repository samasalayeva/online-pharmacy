package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.CategoryRequest;
import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.exceptions.CategoryNotFoundException;
import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.repositories.CategoryRepository;
import com.example.onlinepharmacy.services.abstracts.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepository
                .findAll()
                .stream()
                .map(c-> mapper.map(c,CategoryResponse.class))
                .toList();
    }

   public CategoryResponse get(Long id){
       Category category = categoryRepository
               .findById(id)
               .orElseThrow(
                       () -> new CategoryNotFoundException("this category not found"));
       return mapper.map(category,CategoryResponse.class);
    }

    public CategoryResponse save(CategoryRequest request){
        Category newCategory = Category.builder().name(request.name()).build();
        return mapper.map(categoryRepository.save(newCategory),CategoryResponse.class);
    }

    public CategoryResponse update(Long id, CategoryRequest request){
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException("this category not found"));
        if(request.name()!= null){
            category.setName(request.name());
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
