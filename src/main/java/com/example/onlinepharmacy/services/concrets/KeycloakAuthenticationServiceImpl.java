package com.example.onlinepharmacy.services.concrets;


import com.example.onlinepharmacy.configs.TokenMapper;
import com.example.onlinepharmacy.dtos.response.AccessToken;
import com.example.onlinepharmacy.services.abstracts.AuthenticationService;
import com.example.onlinepharmacy.utils.KeycloakProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAuthenticationServiceImpl implements AuthenticationService {
    private final KeycloakProvider keycloakClient;
    private final RestTemplate restTemplate;

    @Override
    public AccessToken authenticate(String username, char[] password) {
        try {
            Assert.notNull(username, "Username is null");
            Assert.notNull(password, "Password is null");


            var authzClient = keycloakClient.authzClient();
            var authResponse = authzClient.obtainAccessToken(username, new String(password));
            
            return TokenMapper.INSTANCE.map(authResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public AccessToken refreshToken(String refreshToken) {
        try {
            Assert.notNull(refreshToken, "Refresh token is null");

            var refreshTokenRequest = keycloakClient.getClient();
            refreshTokenRequest.set("refresh_token", refreshToken);
            refreshTokenRequest.set("grant_type", "refresh_token");

            var headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            headers.set(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
            var request = new HttpEntity<>(refreshTokenRequest,headers);

            var authResponse =  restTemplate.postForEntity(keycloakClient.getTokenUrl(),request, AccessTokenResponse.class);
            return TokenMapper.INSTANCE.map(authResponse.getBody());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
