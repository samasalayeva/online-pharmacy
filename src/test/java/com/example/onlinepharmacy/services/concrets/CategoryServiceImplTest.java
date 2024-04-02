package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.exceptions.CategoryNotFoundException;
import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.repositories.CategoryRepository;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.example.onlinepharmacy.models.Pharmacy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private  CategoryRepository categoryRepository;

    @Mock
    private  ModelMapper mapper;

    @Mock
    private  PharmacyRepository pharmacyRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testGetAllCategories() {

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Oncological");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Surgery");

        List<Category> categoryList = Arrays.asList(category1, category2);

        CategoryResponse categoryResponse1 = new CategoryResponse();
        categoryResponse1.setId(1L);
        categoryResponse1.setName("Oncological");

        CategoryResponse categoryResponse2 = new CategoryResponse();
        categoryResponse2.setId(2L);
        categoryResponse2.setName("Surgery");

        List<CategoryResponse> expectedResponses = Arrays.asList(categoryResponse1, categoryResponse2);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(mapper.map(category1, CategoryResponse.class)).thenReturn(categoryResponse1);
        when(mapper.map(category2, CategoryResponse.class)).thenReturn(categoryResponse2);


        List<CategoryResponse> actualResponses = categoryService.getAll();


        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).getId(), actualResponses.get(0).getId());
        assertEquals(expectedResponses.get(0).getName(), actualResponses.get(0).getName());
        assertEquals(expectedResponses.get(1).getId(), actualResponses.get(1).getId());
        assertEquals(expectedResponses.get(1).getName(), actualResponses.get(1).getName());
    }


    @Test
    public void testGetCategoryByPharmacy() {
        Long pharmacyId = 1L;

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(pharmacyId);

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Oncological");
        category1.setPharmacy(pharmacy);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Surgery");
        category2.setPharmacy(pharmacy);

        List<Category> categoryList = Arrays.asList(category1, category2);

        CategoryResponse categoryResponse1 = new CategoryResponse();
        categoryResponse1.setId(1L);
        categoryResponse1.setName("Oncological");

        CategoryResponse categoryResponse2 = new CategoryResponse();
        categoryResponse2.setId(2L);
        categoryResponse2.setName("Surgery");

        List<CategoryResponse> expectedResponses = Arrays.asList(categoryResponse1, categoryResponse2);

        when(categoryRepository.findByPharmacyId(pharmacyId)).thenReturn(categoryList);
        when(mapper.map(category1, CategoryResponse.class)).thenReturn(categoryResponse1);
        when(mapper.map(category2, CategoryResponse.class)).thenReturn(categoryResponse2);

        List<CategoryResponse> actualResponses = categoryService.getCategoryByPharmacy(pharmacyId);

        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).getId(), actualResponses.get(0).getId());
        assertEquals(expectedResponses.get(0).getName(), actualResponses.get(0).getName());
        assertEquals(expectedResponses.get(1).getId(), actualResponses.get(1).getId());
        assertEquals(expectedResponses.get(1).getName(), actualResponses.get(1).getName());
    }
    @Test
    public void testSaveCategory() {

        String categoryName = "TestCategory";
        String username = "testUser";

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        Category newCategory = Category.builder().name(categoryName).pharmacy(pharmacy).build();

        CategoryResponse expectedResponse = new CategoryResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName(categoryName);

        when(pharmacyRepository.findByOwnerUsername(username)).thenReturn(Optional.of(pharmacy));
        when(categoryRepository.save(newCategory)).thenReturn(newCategory);
        when(mapper.map(newCategory, CategoryResponse.class)).thenReturn(expectedResponse);


        CategoryResponse actualResponse = categoryService.save(categoryName, username);


        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        verify(pharmacyRepository, times(1)).findByOwnerUsername(username);
        verify(categoryRepository, times(1)).save(newCategory);
        verify(mapper, times(1)).map(newCategory, CategoryResponse.class);
    }

    @Test
    void updateCategory_shouldUpdateCategoryName_whenCategoryExists() {

        Long categoryId = 1L;
        String newCategoryName = "Dentist";

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Stomatology");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName(newCategoryName);

        CategoryResponse expectedResponse = new CategoryResponse();
        expectedResponse.setId(categoryId);
        expectedResponse.setName(newCategoryName);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        when(mapper.map(updatedCategory, CategoryResponse.class)).thenReturn(expectedResponse);

        CategoryResponse actualResponse = categoryService.update(categoryId, newCategoryName);


        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
        verify(mapper, times(1)).map(updatedCategory, CategoryResponse.class);
    }

    @Test
    void deleteCategory_ShouldDeleteCategory_WhenCategoryExists() {
        // Arrange
        Long categoryId = 1L;

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Category 1");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // Act
        categoryService.delete(categoryId);

        // Assert
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(existingCategory);
    }

    @Test
    void deleteCategory_ShouldThrowCategoryNotFoundException_WhenCategoryDoesNotExist() {
        // Arrange
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(categoryId));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any());
    }

}