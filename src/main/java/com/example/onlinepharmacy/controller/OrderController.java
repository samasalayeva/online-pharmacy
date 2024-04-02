package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.AddCartItemRequest;
import com.example.onlinepharmacy.dtos.request.OrderRequest;
import com.example.onlinepharmacy.dtos.response.PaymentResponse;
import com.example.onlinepharmacy.models.CartItem;
import com.example.onlinepharmacy.models.Order;
import com.example.onlinepharmacy.services.abstracts.OrderService;
import com.example.onlinepharmacy.services.abstracts.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('user_client_role')")
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request, Principal principal) throws StripeException {
        Order order = orderService.createOrder(request, principal);
        PaymentResponse res = paymentService.createPaymentLink(order);
        return ResponseEntity.ok(res);
    }
    @GetMapping
    public ResponseEntity<?> getOrderHistory(Principal principal) {
        List<Order> usersOrders = orderService.getUsersOrder(principal.getName());
        return ResponseEntity.ok(usersOrders);
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order canceled");
    }
}
