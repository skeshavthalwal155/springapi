package com.example.springapi.services;

import com.example.springapi.dtos.CheckoutRequest;
import com.example.springapi.dtos.CheckoutResponse;
import com.example.springapi.entities.Order;
import com.example.springapi.exceptions.CartEmptyException;
import com.example.springapi.exceptions.CartNotFoundException;
import com.example.springapi.repositories.CartRepository;
import com.example.springapi.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
