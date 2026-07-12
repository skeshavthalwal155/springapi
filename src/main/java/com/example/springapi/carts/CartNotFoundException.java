package com.example.springapi.carts;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException() {
        super("Cart not found");
    }
}
