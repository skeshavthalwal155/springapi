package com.example.springapi.controller;

import com.example.springapi.dtos.AddItemToCartRequest;
import com.example.springapi.dtos.CartDto;
import com.example.springapi.dtos.CartItemDto;
import com.example.springapi.dtos.UpdateCartItemRequest;
import com.example.springapi.exceptions.CartNotFoundException;
import com.example.springapi.exceptions.ProductNotFoundException;
import com.example.springapi.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
       ){
        var cartDto= cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
       }

       @PostMapping("/{cartId}/items")
       public ResponseEntity<CartItemDto> addToCart(
               @PathVariable UUID cartId,
               @Valid @RequestBody AddItemToCartRequest request
       ){
            var cartItemDTo = cartService.addToCart(cartId, request.getProductId());

            return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDTo);
       }

       @GetMapping("/{cartId}")
       public CartDto getCart(@PathVariable UUID cartId){
            return cartService.getCart(cartId);
       }

       @PutMapping("/{cartId}/items/{productId}")
       public CartItemDto updateItem(
               @PathVariable("cartId") UUID cartId,
               @PathVariable("productId") Long productId,
               @Valid @RequestBody UpdateCartItemRequest request
       ){
           return cartService.updateItem(cartId, productId, request.getQuantity());
       }

       @DeleteMapping("/{cartId}/items/{productId}")
       public ResponseEntity<?> removeItem(
               @PathVariable("cartId") UUID cartId,
               @PathVariable("productId") Long productId
       ){
        cartService.removeItem(cartId, productId);
        return ResponseEntity.noContent().build();
       }
       @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
       }

       @ExceptionHandler(CartNotFoundException.class)
       public ResponseEntity<Map<String, String>> handleCartNotFound(){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found.")
            );
       }
       @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Product was not found in the cart.")
        );
       }
}
