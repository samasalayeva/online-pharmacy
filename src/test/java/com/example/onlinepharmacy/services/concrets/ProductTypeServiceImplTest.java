package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.response.ProductTypeResponse;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.ProductType;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.repositories.ProductTypeRepository;
import com.example.onlinepharmacy.services.abstracts.ProductTypeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductTypeServiceImplTest {

    @Mock
    private ProductTypeRepository productTypeRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductTypeServiceImpl productTypeService;

    @Test
    public void testGetAll() {
            // Given
            ProductType productType1 = new ProductType();
            productType1.setId(1L);
            productType1.setName("Type 1");

            ProductType productType2 = new ProductType();
            productType2.setId(2L);
            productType2.setName("Type 2");

            List<ProductType> productTypeList = Arrays.asList(productType1, productType2);

            ProductTypeResponse productTypeResponse1 = new ProductTypeResponse();
            productTypeResponse1.setId(1L);
            productTypeResponse1.setName("Type 1");

            ProductTypeResponse productTypeResponse2 = new ProductTypeResponse();
            productTypeResponse2.setId(2L);
            productTypeResponse2.setName("Type 2");

            List<ProductTypeResponse> expectedResponses = Arrays.asList(productTypeResponse1, productTypeResponse2);

            when(productTypeRepository.findAll()).thenReturn(productTypeList);
            when(mapper.map(productType1, ProductTypeResponse.class)).thenReturn(productTypeResponse1);
            when(mapper.map(productType2, ProductTypeResponse.class)).thenReturn(productTypeResponse2);

            // When
            List<ProductTypeResponse> actualResponses = productTypeService.getAll();

            // Then
            assertEquals(expectedResponses.size(), actualResponses.size());
            assertEquals(expectedResponses.get(0).getId(), actualResponses.get(0).getId());
            assertEquals(expectedResponses.get(0).getName(), actualResponses.get(0).getName());
            assertEquals(expectedResponses.get(1).getId(), actualResponses.get(1).getId());
            assertEquals(expectedResponses.get(1).getName(), actualResponses.get(1).getName());

    }

    @Test
    void getProductTypeByPharmacy_ShouldReturnProductTypesForGivenPharmacy() {
        // Given
        Pharmacy pharmacy = new Pharmacy();
        Long pharmacyId = 1L;
        pharmacy.setId(pharmacyId);

        ProductType productType1 = new ProductType();
        productType1.setId(1L);
        productType1.setName("Type 1");
        productType1.setPharmacy(pharmacy);

        ProductType productType2 = new ProductType();
        productType2.setId(2L);
        productType2.setName("Type 2");
        productType2.setPharmacy(pharmacy);

        List<ProductType> productTypeList = Arrays.asList(productType1, productType2);

        ProductTypeResponse productTypeResponse1 = new ProductTypeResponse();
        productTypeResponse1.setId(1L);
        productTypeResponse1.setName("Type 1");

        ProductTypeResponse productTypeResponse2 = new ProductTypeResponse();
        productTypeResponse2.setId(2L);
        productTypeResponse2.setName("Type 2");

        List<ProductTypeResponse> expectedResponses = Arrays.asList(productTypeResponse1, productTypeResponse2);

        when(productTypeRepository.findByPharmacyId(pharmacyId)).thenReturn(productTypeList);
        when(mapper.map(productType1, ProductTypeResponse.class)).thenReturn(productTypeResponse1);
        when(mapper.map(productType2, ProductTypeResponse.class)).thenReturn(productTypeResponse2);

        // When
        List<ProductTypeResponse> actualResponses = productTypeService.getProductTypeByPharmacy(pharmacyId);

        // Then
        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).getId(), actualResponses.get(0).getId());
        assertEquals(expectedResponses.get(0).getName(), actualResponses.get(0).getName());
        assertEquals(expectedResponses.get(1).getId(), actualResponses.get(1).getId());
        assertEquals(expectedResponses.get(1).getName(), actualResponses.get(1).getName());
    }

    @Test
    void save_ShouldSaveProductTypeWithGivenNameAndPharmacyOwnerUsername() {
        // Given
        String productTypeName = "Capsule";
        String username = "Gunay";

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        ProductType newProductType = ProductType.builder().name(productTypeName).pharmacy(pharmacy).build();

        ProductTypeResponse expectedResponse = new ProductTypeResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName(productTypeName);

        when(pharmacyRepository.findByOwnerUsername(username)).thenReturn(Optional.of(pharmacy));
        when(productTypeRepository.save(newProductType)).thenReturn(newProductType);
        when(mapper.map(newProductType, ProductTypeResponse.class)).thenReturn(expectedResponse);

        // When
        ProductTypeResponse actualResponse = productTypeService.save(productTypeName, username);

        // Then
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        verify(pharmacyRepository, times(1)).findByOwnerUsername(username);
        verify(productTypeRepository, times(1)).save(newProductType);
        verify(mapper, times(1)).map(newProductType, ProductTypeResponse.class);
    }

    @Test
    void get_ShouldReturnProductTypeForGivenId() {
        // Given
        Long typeId = 1L;

        ProductType productType = new ProductType();
        productType.setId(typeId);
        productType.setName("Type 1");

        ProductTypeResponse expectedResponse = new ProductTypeResponse();
        expectedResponse.setId(typeId);
        expectedResponse.setName("Type 1");

        when(productTypeRepository.findById(typeId)).thenReturn(Optional.of(productType));
        when(mapper.map(productType, ProductTypeResponse.class)).thenReturn(expectedResponse);

        // When
        ProductTypeResponse actualResponse = productTypeService.get(typeId);

        // Then
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    void update_ShouldUpdateProductTypeForGivenId() {
        // Given
        Long typeId = 1L;
        String newTypeName = "Updated Type";

        ProductType existingProductType = new ProductType();
        existingProductType.setId(typeId);
        existingProductType.setName("Type 1");

        ProductType updatedProductType = new ProductType();
        updatedProductType.setId(typeId);
        updatedProductType.setName(newTypeName);

        ProductTypeResponse expectedResponse = new ProductTypeResponse();
        expectedResponse.setId(typeId);
        expectedResponse.setName(newTypeName);

        when(productTypeRepository.findById(typeId)).thenReturn(Optional.of(existingProductType));
        when(productTypeRepository.save(existingProductType)).thenReturn(updatedProductType);
        when(mapper.map(updatedProductType, ProductTypeResponse.class)).thenReturn(expectedResponse);

        // When
        ProductTypeResponse actualResponse = productTypeService.update(typeId, newTypeName);

        // Then
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    void delete_ShouldDeleteProductTypeForGivenId() {
        // Given
        Long typeId = 1L;

        ProductType existingProductType = new ProductType();
        existingProductType.setId(typeId);
        existingProductType.setName("Type 1");

        when(productTypeRepository.findById(typeId)).thenReturn(Optional.of(existingProductType));

        // When
        productTypeService.delete(typeId);

        // Then
        verify(productTypeRepository, times(1)).delete(existingProductType);
    }


}