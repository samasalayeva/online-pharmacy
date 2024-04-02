package com.example.onlinepharmacy.dtos.response;

import com.example.onlinepharmacy.models.Category;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.ProductImage;
import com.example.onlinepharmacy.models.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private LocalDate creationDate;
    private ProductType productType;
    private Category category;
    private Pharmacy pharmacy;
    List<ProductImage> productImages = new ArrayList<>();
}
