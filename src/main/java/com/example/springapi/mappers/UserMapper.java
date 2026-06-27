package com.example.springapi.mappers;

import com.example.springapi.dtos.UserDto;
import com.example.springapi.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.springapi.dtos.RegisterUserRequest;
import com.example.springapi.dtos.UpdateUserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
