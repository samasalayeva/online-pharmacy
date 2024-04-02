package com.example.onlinepharmacy.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
