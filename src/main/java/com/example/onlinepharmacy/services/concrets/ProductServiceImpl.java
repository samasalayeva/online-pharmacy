package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.ProductRequest;
import com.example.onlinepharmacy.dtos.response.ProductResponse;
import com.example.onlinepharmacy.exceptions.CategoryNotFoundException;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.Product;
import com.example.onlinepharmacy.models.ProductType;
import com.example.onlinepharmacy.repositories.CategoryRepository;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.repositories.ProductRepository;
import com.example.onlinepharmacy.repositories.ProductTypeRepository;
import com.example.onlinepharmacy.services.abstracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PharmacyRepository pharmacyRepository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;
    private final ProductTypeRepository productTypeRepository;

    public ProductResponse save(ProductRequest request) {
        Product newProduct = mapper.map(request, Product.class);
        newProduct.setCreationDate(LocalDate.now());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("This category not found"));
        newProduct.setCategory(category);

        ProductType productType = productTypeRepository
                .findById(request.getProductTypeId())
                .orElseThrow(() -> new NotFoundException("Product type not found"));
        newProduct.setProductType(productType);
        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() ->
                        new NotFoundException("Pharmacy not found " + request.getPharmacyId() + " with id"));
        newProduct.setPharmacy(pharmacy);
        return mapper.map(productRepository.save(newProduct), ProductResponse.class);
    }

    public List<ProductResponse> getAll() {
        return productRepository
                .findAll()
                .stream()
                .map(p -> mapper.map(p, ProductResponse.class))
                .toList();
    }

    public ProductResponse get(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("this product does not exist"));
        return mapper.map(product, ProductResponse.class);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This product does not exist"));

        product.setName(Objects.nonNull(request.getName()) ? request.getName() : product.getName());
        product.setDescription(Objects.nonNull(request.getDescription()) ? request.getDescription() : product.getDescription());
        product.setPrice(Objects.nonNull(request.getPrice()) ? request.getPrice() : product.getPrice());
        product.setStock(Objects.nonNull(request.getStock()) ? request.getStock() : product.getStock());
        Optional.ofNullable(request.getCategoryId())
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException("This category not found")))
                .ifPresent(product::setCategory);

        Optional.ofNullable(request.getProductTypeId())
                .map(productTypeId -> productTypeRepository.findById(productTypeId)
                        .orElseThrow(() -> new NotFoundException("This product type not found")))
                .ifPresent(product::setProductType);

        Optional.ofNullable(request.getPharmacyId())
                .map(pharmacyId -> pharmacyRepository.findById(pharmacyId)
                        .orElseThrow(() -> new NotFoundException("This pharmacy not found"))).ifPresent(product::setPharmacy);
        return mapper.map(productRepository.save(product), ProductResponse.class);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This product does not exist"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> getProductByPharmacyId(Long pharmacyId) {
        return productRepository
                .findByPharmacyId(pharmacyId)
                .stream()
                .map(p -> mapper.map(p, ProductResponse.class))
                .toList();
    }

    @Override
    public List<ProductResponse> getProductByCategoryId(Long categoryId) {
        return productRepository
                .findByCategory_Id(categoryId)
                .stream()
                .map(p -> mapper.map(p, ProductResponse.class))
                .toList();
    }

    @Override
    public List<ProductResponse> getProductByProductType(Long productTypeId) {
        return productRepository
                .findByProductTypeId(productTypeId)
                .stream()
                .map(p -> mapper.map(p, ProductResponse.class))
                .toList();
    }

    @Override
    public List<ProductResponse> findByProductTypeAndCategory(Long productTypeId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("This category not found"));


        ProductType productType = productTypeRepository
                .findById(productTypeId)
                .orElseThrow(() -> new NotFoundException("Product type not found"));

        return  productRepository
                .findByProductTypeAndCategory(productType,category)
                .stream()
                .map(p -> mapper.map(p, ProductResponse.class))
                .toList();
    }


}
