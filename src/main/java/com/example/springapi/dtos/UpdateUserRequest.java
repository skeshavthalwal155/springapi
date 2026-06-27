package com.example.springapi.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
