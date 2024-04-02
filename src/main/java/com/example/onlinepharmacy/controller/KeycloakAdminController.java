package com.example.onlinepharmacy.controller;


import com.example.onlinepharmacy.services.abstracts.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/keycloak")
@RequiredArgsConstructor
public class KeycloakAdminController {


    private final KeycloakService keycloakService;

    @PreAuthorize("hasRole('super_admin_role')")
    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(keycloakService.findAllUsers());
    }

    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(keycloakService.searchUserByUsername(username));
    }

    @PreAuthorize("hasRole('admin_client_role')")
    @PatchMapping("/block-user/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable String userId){
        keycloakService.blockUser(userId);
        return ResponseEntity.ok("User blocked successfully");
    }

    @PreAuthorize("hasRole('super_admin_role')")
    @PutMapping("/assign-role/user/{userId}")
    public ResponseEntity<?>  assignRole(@PathVariable String userId, @RequestParam String roleName){
        keycloakService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
