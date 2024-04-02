package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.AddCartItemRequest;
import com.example.onlinepharmacy.models.Cart;
import com.example.onlinepharmacy.models.CartItem;

import java.math.BigDecimal;
import java.security.Principal;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequest request, Principal principal);
    CartItem updateCartItemQuantity(Long cartItemId, int quantity);

    public Cart removeItemFromCart(Long cartItemId, Principal principal);
    BigDecimal calculateCartTotals(Cart cart);

    Cart findCartById(Long id);

    Cart findCartByUser(Principal principal);

    Cart clearCart(Principal principal);
}
