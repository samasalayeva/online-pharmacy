package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.UserDTO;
import com.example.onlinepharmacy.dtos.response.AccessToken;
import com.example.onlinepharmacy.services.abstracts.AuthenticationService;
import com.example.onlinepharmacy.services.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws URISyntaxException {
        String response = userService.register(userDTO);
        return ResponseEntity.created(new URI("/api/auth/register")).body(response);
    }
    @PostMapping
    public ResponseEntity<AccessToken> signIn(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(authenticationService.authenticate(username, password.toCharArray()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> getRefreshToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }


}
