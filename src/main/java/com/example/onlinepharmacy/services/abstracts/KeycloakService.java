package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakService {

    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserDTO userDTO);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO userDTO);
    void emailVerification(String userId);
    void assignRole(String userId, String roleName);
    void forgotPassword(String email);
    void updatePassword(String userId);
}
