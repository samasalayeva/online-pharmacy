package com.example.onlinepharmacy.dtos.request;

import com.example.onlinepharmacy.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Address deliveryAddress;
    private Long pharmacyId;
}
