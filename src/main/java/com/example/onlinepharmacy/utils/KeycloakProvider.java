package com.example.onlinepharmacy.utils;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
@Component
public class KeycloakProvider {
    private static final String SERVER_URL = "http://localhost:8181";
    private static final String REALM_NAME = "spring-boot-keycloak";
    private static final String REALM_MASTER = "master";
    private static final String ADMIN_CLI = "admin-cli";
    private static final String USER_CONSOLE = "admin";
    private static final String PASSWORD_CONSOLE = "admin";
    private static final String CLIENT_SECRET = "DfAZYr7yz8dyRYbSHgQDnTGfnYszxTt9";

    public static RealmResource getRealmResource() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER)
                .clientId(ADMIN_CLI)
                .username(USER_CONSOLE)
                .password(PASSWORD_CONSOLE)
                .clientSecret(CLIENT_SECRET)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();

        return keycloak.realm(REALM_NAME);
    }

    public static UsersResource getUsersResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
    public static UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    public AuthzClient authzClient(){
        var clientCredentials = new HashMap<String, Object>();
        clientCredentials.put("secret", CLIENT_SECRET);
        var configuration =
                new Configuration(SERVER_URL , REALM_NAME, "online-pharmacy", clientCredentials, null);
        return AuthzClient.create(configuration);
    }

    public String getTokenUrl(){
        return SERVER_URL+"/realms/"+REALM_NAME+"/protocol/openid-connect/token";
    }

    public MultiValueMap<String, String> getClient(){
        var map =  new LinkedMultiValueMap<String, String>();
        map.set("client_id", "online-pharmacy");
        map.set("client_secret", CLIENT_SECRET);
        return map;
    }

}
