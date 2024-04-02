package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.dtos.response.ProductResponse;
import com.example.onlinepharmacy.dtos.response.ProductTypeResponse;
import com.example.onlinepharmacy.exceptions.CategoryNotFoundException;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.ProductType;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.repositories.ProductTypeRepository;
import com.example.onlinepharmacy.services.abstracts.ProductService;
import com.example.onlinepharmacy.services.abstracts.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ModelMapper mapper;
    private final PharmacyRepository pharmacyRepository;

    @Override
    public List<ProductTypeResponse> getAll() {
        return productTypeRepository
                .findAll()
                .stream()
                .map(c-> mapper.map(c,ProductTypeResponse.class))
                .toList();
    }

    @Override
    public List<ProductTypeResponse> getProductTypeByPharmacy(Long pharmacyId) {
        return productTypeRepository
                .findByPharmacyId(pharmacyId)
                .stream()
                .map(p -> mapper.map(p, ProductTypeResponse.class)).toList();
    }

    @Override
    public ProductTypeResponse get(Long id) {
        ProductType productType = productTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("this type not found"));
        return mapper.map(productType,ProductTypeResponse.class);
    }

    @Override
    public ProductTypeResponse save(String productType, String username) {
        Pharmacy pharmacy = pharmacyRepository.findByOwnerUsername(username).orElseThrow(()
                -> new NotFoundException("Pharmacy not found"));

        ProductType newProductType = ProductType.builder().name(productType).pharmacy(pharmacy).build();
        return mapper.map(productTypeRepository.save(newProductType),ProductTypeResponse.class);

    }

    @Override
    public ProductTypeResponse update(Long id, String productType) {
        ProductType findProductType = productTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("this type not found"));
        if(productType != null){
            findProductType.setName(productType);
        }
        return mapper.map(productTypeRepository.save(findProductType),ProductTypeResponse.class);

    }

    @Override
    public void delete(Long id) {

            ProductType productType = productTypeRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("this category not found"));
            productTypeRepository.delete(productType);

    }
}
