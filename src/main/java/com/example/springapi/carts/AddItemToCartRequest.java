package com.example.springapi.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull(message = "Product Id can't be null")
    private Long productId;
}
