package com.example.springapi.mappers;

import com.example.springapi.dtos.CartDto;
import com.example.springapi.dtos.CartItemDto;
import com.example.springapi.entities.Cart;
import com.example.springapi.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice", expression="java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}

