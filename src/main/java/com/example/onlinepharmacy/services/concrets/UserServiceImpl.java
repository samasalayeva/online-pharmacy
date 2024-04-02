package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.UpdateUserRequest;
import com.example.onlinepharmacy.dtos.request.UserDTO;
import com.example.onlinepharmacy.dtos.response.UserResponse;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Cart;
import com.example.onlinepharmacy.models.User;
import com.example.onlinepharmacy.repositories.CartRepository;
import com.example.onlinepharmacy.repositories.UserRepository;
import com.example.onlinepharmacy.services.abstracts.KeycloakService;
import com.example.onlinepharmacy.services.abstracts.UserService;
import com.example.onlinepharmacy.utils.KeycloakUserIdProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ModelMapper mapper;
    private final JwtDecoder jwtDecoder;

    @Override
    public String register(@NonNull UserDTO userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = mapper.map(userDto, User.class);

        boolean registeredToKeycloak = keycloakService.registerToKeycloak(userDto);
        if (registeredToKeycloak) {
            user.setRegistrationDate(LocalDate.now());
            userRepository.save(user);
            if(Objects.equals(userDto.getRole(), "user")){
                Cart cart = new Cart();
                cart.setUser(user);
                cartRepository.save(cart);
            }

        }
        return "Please check your email";
    }

    @Override
    public void deleteUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user);
        if(cart != null){
            cartRepository.delete(cart);
        }
        userRepository.delete(user);
        String keycloakUserId = KeycloakUserIdProvider.getKeycloakUserId(principal);
        keycloakService.deleteUser(keycloakUserId);
    }

    @Override
    public String updateUser(Principal principal, UpdateUserRequest request) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (user.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        String keycloakUserId = KeycloakUserIdProvider.getKeycloakUserId(principal);
        keycloakService.updateUser(keycloakUserId,request);

        return "User updated successfully";
    }

    public UserResponse getUser(String accessToken) {
        Jwt jwtDecode = jwtDecoder.decode(accessToken.replace("Bearer ", ""));

        if (jwtDecode == null) {
            throw  new RuntimeException("Unauthorized");
        }
        Map<String, Object> resourceAccess = (Map<String, Object>) jwtDecode.getClaims().get("resource_access");
        Map<String, Object> onlinePharmacyRoles = (Map<String, Object>) resourceAccess.get("online-pharmacy");
        List<String> roles = (List<String>) onlinePharmacyRoles.get("roles");


        Authentication authentication = new JwtAuthenticationToken(jwtDecode);
        Jwt jwt = (Jwt) authentication.getCredentials();
        String username = jwt.getClaim("preferred_username");

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found " + username));
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        userResponse.setRoles(roles);
        log.warn("roles {}",roles);
        return userResponse;
    }
}
