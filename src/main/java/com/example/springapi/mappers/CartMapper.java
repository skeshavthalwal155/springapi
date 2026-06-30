package com.example.springapi.mappers;

import com.example.springapi.dtos.CartDto;
import com.example.springapi.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
}

