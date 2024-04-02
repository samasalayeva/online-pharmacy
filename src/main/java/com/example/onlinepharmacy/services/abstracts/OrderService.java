package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.OrderRequest;
import com.example.onlinepharmacy.enums.OrderStatus;
import com.example.onlinepharmacy.models.Order;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request, Principal principal);
    Order updateOrder(Long orderId, OrderStatus status) throws Exception;

    void cancelOrder(Long orderId);
    List<Order> getUsersOrder(String username);
    List<Order> getPharmaciesOrder(Long pharmacyId, OrderStatus orderStatus);

    List<Order> get();
    Order getOne(Long orderId);


}
