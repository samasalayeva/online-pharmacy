package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.response.AccessToken;

public interface AuthenticationService {
    AccessToken authenticate(String username, char[] password);
    AccessToken refreshToken(String refreshToken);
}
