package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.AddCartItemRequest;
import com.example.onlinepharmacy.dtos.request.UpdateCartItemRequest;
import com.example.onlinepharmacy.models.Cart;
import com.example.onlinepharmacy.models.CartItem;
import com.example.onlinepharmacy.services.abstracts.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('user_client_role')")
public class CartController {

    private final CartService cartService;

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request, Principal principal) {
        CartItem cartItem = cartService.addItemToCart(request, principal);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request) {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id,  Principal principal) {
        Cart cart = cartService.removeItemFromCart(id, principal);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(Principal principal) {
        Cart cart = cartService.clearCart(principal);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<Cart> findUserCart(Principal principal) {
        Cart cart = cartService.findCartByUser(principal);
        return ResponseEntity.ok(cart);
    }

}
