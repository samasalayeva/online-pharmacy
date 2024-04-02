package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.enums.OrderStatus;
import com.example.onlinepharmacy.models.Order;
import com.example.onlinepharmacy.services.abstracts.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('admin_client_role')")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @GetMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long pharmacyId, @RequestParam OrderStatus orderStatus) {
        List<Order> pharmacyOrders = orderService.getPharmaciesOrder(pharmacyId, orderStatus);
        return ResponseEntity.ok(pharmacyOrders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus) throws Exception {
        Order order = orderService.updateOrder(orderId, orderStatus);
        return ResponseEntity.ok(order);
    }
}
