package com.example.onlinepharmacy.utils;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
public class KeycloakUserIdProvider {
    public static String getKeycloakUserId(Principal principal) {

        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            return keycloakPrincipal.getKeycloakSecurityContext().getToken().getSubject();
        } else if (principal instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) principal;
            Jwt jwt = (Jwt) jwtToken.getPrincipal();
            return jwt.getSubject();
        } else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
        }
    }

    public static String getEmailFromPrincipal(Principal principal) {
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            return keycloakPrincipal.getKeycloakSecurityContext().getToken().getEmail();
        } else if (principal instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) principal;
            Jwt jwt = (Jwt) jwtToken.getPrincipal();
            return jwt.getClaim("email");
        } else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
        }
    }


}
