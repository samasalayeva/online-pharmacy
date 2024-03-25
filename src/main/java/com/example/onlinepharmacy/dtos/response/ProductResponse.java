package com.example.onlinepharmacy.dtos.response;

import com.example.onlinepharmacy.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Category category;
}
