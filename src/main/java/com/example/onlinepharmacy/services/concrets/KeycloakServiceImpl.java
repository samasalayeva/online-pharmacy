package com.example.onlinepharmacy.services.concrets;


import com.example.onlinepharmacy.dtos.request.UpdateCartItemRequest;
import com.example.onlinepharmacy.dtos.request.UpdateUserRequest;
import com.example.onlinepharmacy.dtos.request.UserDTO;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.repositories.CartRepository;
import com.example.onlinepharmacy.services.abstracts.KeycloakService;
import com.example.onlinepharmacy.utils.KeycloakProvider;
import com.example.onlinepharmacy.utils.KeycloakUserIdProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.onlinepharmacy.utils.KeycloakProvider.*;
import static com.example.onlinepharmacy.utils.KeycloakProvider.getUsersResource;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getRealmResource()
                .users()
                .list();
    }


    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloakProvider.getRealmResource()
                .users()
                .searchByUsername(username, true);
    }


    public boolean registerToKeycloak(@NonNull UserDTO userDTO) {

        int status;
        UsersResource usersResource = getUsersResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        Response response = usersResource.create(userRepresentation);

        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            usersResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<RoleRepresentation> rolesRepresentation;

            if (userDTO.getRole().equals("admin")) {
                rolesRepresentation = List.of(realmResource.roles().get("admin").toRepresentation());

            } else {
                rolesRepresentation = List.of(realmResource.roles().get("user").toRepresentation());

            }


            List<UserRepresentation> representationList = usersResource.searchByUsername(userDTO.getUsername(), true);

            if (!CollectionUtils.isEmpty(representationList)) {
                UserRepresentation userRepresentation1 = representationList.stream().filter(u -> Objects.equals(false, u.isEmailVerified())).findFirst().orElse(null);
                assert userRepresentation1 != null;
                emailVerification(userRepresentation1.getId());
            }

            realmResource.users().get(userId).roles().realmLevel().add(rolesRepresentation);


        }
        return status == 201;
    }


    public void deleteUser(String userId) {
        getUsersResource()
                .get(userId)
                .remove();
    }


    public void updateUser(String userId, @NonNull UpdateUserRequest request) {

        UserRepresentation user = new UserRepresentation();
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
            user.setEmailVerified(false);
        } else {
            user.setEmailVerified(true);
        }

        user.setEnabled(true);


        UserResource usersResource = getUsersResource().get(userId);
        usersResource.update(user);
    }

    public void emailVerification(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public void assignRole(String userId, String roleName) {

        UserResource userResource = getUserResource(userId);
        RolesResource rolesResource = getRealmResource().roles();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));

    }

    @Override
    public void forgotPassword(String email) {
        List<UserRepresentation> representationList = getUsersResource()
                .searchByEmail(email, true);
        UserRepresentation userRepresentation = representationList
                .stream()
                .findFirst()
                .orElse(null);
        if (userRepresentation != null) {
            UserResource userResource = getUsersResource().get(userRepresentation.getId());
            List<String> actions = new ArrayList<>();
            actions.add("UPDATE_PASSWORD");
            userResource.executeActionsEmail(actions);
            return;
        }
        throw new NotFoundException("Email not found");
    }

    @Override
    public void updatePassword(String userId) {

        UserResource userResource = getUserResource(userId);
        List<String> actions = new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);

    }

    @Override
    public void blockUser(String userId) {
        UsersResource usersResource = getUsersResource();
        UserRepresentation user = usersResource.get(userId).toRepresentation();
        user.setEnabled(false);
        usersResource.get(userId).update(user);
    }

}
