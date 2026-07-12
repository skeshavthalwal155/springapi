package com.example.springapi.carts;

import lombok.Data;

@Data
public class CartProductDto {
    private Long id;
    private String name;
    private String description;
}
