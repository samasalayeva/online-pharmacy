package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.UserDTO;
import com.example.onlinepharmacy.services.abstracts.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

@RestController
@RequestMapping("/keycloak/user")
@RequiredArgsConstructor
public class KeycloakController {


    private final KeycloakService keycloakService;

    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping("/search")
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(keycloakService.findAllUsers());
    }

    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(keycloakService.searchUserByUsername(username));
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        String response = keycloakService.createUser(userDTO);
        return ResponseEntity.created(new URI("/keycloak/user/register")).body(response);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(Principal principal, @RequestBody UserDTO userDTO){
        keycloakService.updateUser(principal.getName(), userDTO);
        return ResponseEntity.ok("User updated successfully");
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Principal principal){
        keycloakService.deleteUser(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('admin_client_role')")
    @PutMapping("/assign-role/user/{userId}")
    public ResponseEntity<?>  assignRole(@PathVariable String userId, @RequestParam String roleName){
        keycloakService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/forgot-password/email/{email}")
    public void forgotPassword(@PathVariable String email){
        keycloakService.forgotPassword(email);
    }

    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakService.updatePassword(principal.getName());
    }

}
