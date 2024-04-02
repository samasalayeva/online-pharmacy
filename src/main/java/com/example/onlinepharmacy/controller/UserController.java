package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.UpdateUserRequest;
import com.example.onlinepharmacy.dtos.request.UserDTO;
import com.example.onlinepharmacy.services.abstracts.KeycloakService;
import com.example.onlinepharmacy.services.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final KeycloakService keycloakService;



    @GetMapping("/profile")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok(userService.getUser(accessToken));
    }

    @PutMapping("/forgot-password/email/{email}")
    public void forgotPassword(@PathVariable String email){
        keycloakService.forgotPassword(email);
    }

    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakService.updatePassword(principal.getName());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(Principal principal, @RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(userService.updateUser(principal,request));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Principal principal){
        userService.deleteUser(principal);
        return ResponseEntity.noContent().build();
    }
}
