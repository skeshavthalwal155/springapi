package com.example.springapi.controller;

import com.example.springapi.dtos.CheckoutRequest;
import com.example.springapi.dtos.ErrorDto;
import com.example.springapi.exceptions.CartEmptyException;
import com.example.springapi.exceptions.CartNotFoundException;
import com.example.springapi.services.CheckoutService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
        try {
            return ResponseEntity.ok(checkoutService.checkout(request));
        } catch (StripeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Error Creating a checkout session"));
        }
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
