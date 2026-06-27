package com.example.springapi.mappers;

import com.example.springapi.dtos.UserDto;
import com.example.springapi.entities.User;

import org.mapstruct.Mapper;

import com.example.springapi.dtos.RegisterUserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);
}
