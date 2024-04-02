package com.example.onlinepharmacy.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
