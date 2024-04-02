package com.example.onlinepharmacy.dtos.request;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
}