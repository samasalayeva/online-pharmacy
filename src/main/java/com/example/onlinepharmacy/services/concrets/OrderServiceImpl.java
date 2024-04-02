package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.OrderRequest;
import com.example.onlinepharmacy.enums.OrderStatus;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.*;
import com.example.onlinepharmacy.repositories.*;
import com.example.onlinepharmacy.services.abstracts.CartService;
import com.example.onlinepharmacy.services.abstracts.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final PharmacyRepository pharmacyRepository;
    private final CartService cartService;

    @Override
    public Order createOrder(OrderRequest request, Principal principal) {
        Address address = request.getDeliveryAddress();
        addressRepository.save(address);
        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() ->
                        new NotFoundException("User not found with username"
                                + principal.getName()));
        if (!user.getAddresses().contains(address)) {
            address.setUser(user);
            addressRepository.save(address);
        }
        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(()
                        -> new NotFoundException("Pharmacy not found with id" +
                        request.getPharmacyId()));

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setPharmacy(pharmacy);
        createdOrder.setCreatedAt(LocalDate.now());
        createdOrder.setDeliveryAddress(address);
        createdOrder.setStatus(OrderStatus.PENDING);


        Order savedOrder = orderRepository.save(createdOrder);

        Cart cart = cartService.findCartByUser(principal);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setOrder(savedOrder);
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }


        savedOrder.setOrderItems(orderItems);
        savedOrder.setTotalPrice(cart.getTotal());
        savedOrder.setTotalItem(orderItems.size());


        return orderRepository.save(savedOrder);
    }

    @Override
    public Order updateOrder(Long orderId, OrderStatus status) throws Exception {
        Order order = getOne(orderId);
        if (status.equals(OrderStatus.ON_THE_WAY)
                || status.equals(OrderStatus.DELIVERED)
                || status.equals(OrderStatus.PENDING)) {

            order.setStatus(status);
            return orderRepository.save(order);
        }


        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOne(orderId);
        orderRepository.delete(order);
    }

    @Override
    public List<Order> getUsersOrder(String username) {

        return orderRepository.findByUserUsername(username);
    }

    @Override
    public List<Order> getPharmaciesOrder(Long pharmacyId, OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByPharmacyId(pharmacyId);
        if (orders != null) {
            orders = orders
                    .stream()
                    .filter(order -> order.getStatus().equals(orderStatus))
                    .toList();
        }
        return orders;
    }

    @Override
    public List<Order> get() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new NotFoundException("Order not found with id " + orderId));
    }
}
