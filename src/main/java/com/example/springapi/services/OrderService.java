package com.example.springapi.services;

import com.example.springapi.auth.AuthService;
import com.example.springapi.dtos.OrderDto;
import com.example.springapi.exceptions.OrderNotFoundException;
import com.example.springapi.mappers.OrderMapper;
import com.example.springapi.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if (!order.isPlaceBy(user)) {
            throw new AccessDeniedException("You don't have access to this order");
        }


        return orderMapper.toDto(order);
    }
}
