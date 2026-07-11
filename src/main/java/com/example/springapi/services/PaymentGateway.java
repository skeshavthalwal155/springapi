package com.example.springapi.services;

import com.example.springapi.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
