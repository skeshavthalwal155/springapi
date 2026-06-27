package com.example.springapi.mappers;

import com.example.springapi.dtos.UserDto;
import com.example.springapi.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
