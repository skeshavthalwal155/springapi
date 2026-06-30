package com.example.springapi.controller;

import com.example.springapi.dtos.CartDto;
import com.example.springapi.entities.Cart;
import com.example.springapi.mappers.CartMapper;
import com.example.springapi.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
       @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuider
       ){
            var cart = new Cart();
            cartRepository.save(cart);
            var cartDto = cartMapper.toDto(cart);
            var uri = uriBuider.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
            return ResponseEntity.created(uri).body(cartDto);
       }
}
