package com.example.springapi.services;

import com.example.springapi.dtos.CheckoutRequest;
import com.example.springapi.dtos.CheckoutResponse;
import com.example.springapi.entities.Order;
import com.example.springapi.exceptions.CartEmptyException;
import com.example.springapi.exceptions.CartNotFoundException;
import com.example.springapi.repositories.CartRepository;
import com.example.springapi.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) throws StripeException {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        try {
            // create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel.html");
            order.getItems().forEach(item -> {
                var lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity(Long.valueOf(item.getQuantity()))
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmountDecimal(
                                                item.getUnitPrice()
                                                        .multiply(BigDecimal.valueOf(100)
                                                        ))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName())
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());


            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getUrl());
        } catch (StripeException ex) {
            System.out.println(ex.getMessage());
            orderRepository.delete(order);
            throw ex;
        }
    }
}
