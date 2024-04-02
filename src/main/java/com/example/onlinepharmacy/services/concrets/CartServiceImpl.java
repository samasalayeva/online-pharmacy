package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.AddCartItemRequest;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Cart;
import com.example.onlinepharmacy.models.CartItem;
import com.example.onlinepharmacy.models.Product;
import com.example.onlinepharmacy.models.User;
import com.example.onlinepharmacy.repositories.CartItemRepository;
import com.example.onlinepharmacy.repositories.CartRepository;
import com.example.onlinepharmacy.repositories.ProductRepository;
import com.example.onlinepharmacy.repositories.UserRepository;
import com.example.onlinepharmacy.services.abstracts.CartService;
import com.example.onlinepharmacy.utils.KeycloakUserIdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public CartItem addItemToCart(AddCartItemRequest request, Principal principal) {
        String keycloakUserEmail = KeycloakUserIdProvider.getEmailFromPrincipal(principal);
        User user = userRepository.findByEmail(keycloakUserEmail).orElse(null);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("This product is not exist"));
        Cart cart = cartRepository.findByUser(user);

        for (CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getProduct().equals(product)){
                int newQuantity = cartItem.getQuantity()+request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }

        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setTotalPrice(BigDecimal.valueOf(request.getQuantity()).multiply(product.getPrice()));

        CartItem saveCartItem = cartItemRepository.save(newCartItem);
        cart.getCartItems().add(saveCartItem);

        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository
                .findById(cartItemId).orElseThrow(()
                        -> new NotFoundException("cart item does not exist"));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(BigDecimal
                .valueOf(quantity)
                .multiply(cartItem.getProduct().getPrice()));


        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, Principal principal) {
        String keycloakUserEmail = KeycloakUserIdProvider.getEmailFromPrincipal(principal);
        User user = userRepository.findByEmail(keycloakUserEmail).orElse(null);

        Cart cart = cartRepository.findByUser(user);

        CartItem cartItem = cartItemRepository
                .findById(cartItemId).orElseThrow(()
                        -> new NotFoundException("cart item does not exist"));
        cart.getCartItems().remove(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    public BigDecimal calculateCartTotals(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems() ) {
            total = total.add(cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) {
        return  cartRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Cart not found"));
    }

    @Override
    public Cart findCartByUser(Principal principal) {
        String keycloakUserEmail = KeycloakUserIdProvider.getEmailFromPrincipal(principal);
        User user = userRepository.findByEmail(keycloakUserEmail).orElse(null);

        Cart cart = cartRepository.findByUser(user);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Principal principal) {
        Cart cart = findCartByUser(principal);
        cart.getCartItems().clear();
        cart.setTotal(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }
}
