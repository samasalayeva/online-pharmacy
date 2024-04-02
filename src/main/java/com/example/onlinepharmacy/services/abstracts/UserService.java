package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.UpdateUserRequest;
import com.example.onlinepharmacy.dtos.request.UserDTO;
import com.example.onlinepharmacy.dtos.response.UserResponse;

import java.security.Principal;

public interface UserService {
    String register(UserDTO userDto);
    void deleteUser(Principal principal);
    String updateUser(Principal principal, UpdateUserRequest request);

    UserResponse getUser(String accessToken);
}
