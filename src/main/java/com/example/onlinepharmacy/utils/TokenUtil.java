package com.example.onlinepharmacy.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
@RequiredArgsConstructor
@Component
public class TokenUtil {
private  final  JwtDecoder jwtDecoder;
    public Principal getPrincipal(String accessToken){
        Jwt jwtDecode = jwtDecoder.decode(accessToken.replace("Bearer ", ""));
        return new JwtAuthenticationToken(jwtDecode);
    }
}
