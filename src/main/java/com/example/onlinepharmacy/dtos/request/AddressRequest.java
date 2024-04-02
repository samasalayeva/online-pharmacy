package com.example.onlinepharmacy.dtos.request;

import com.example.onlinepharmacy.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private Long id;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
    private User user;
}
