package com.example.onlinepharmacy.dtos.request;

import lombok.Data;

@Data
public class PharmacyRequest {
    private String name;
    private String description;
    private AddressRequest address;
    private ContactInformation contactInformation;
    private String openingHours;
}
