package com.example.springapi.mappers;

import com.example.springapi.dtos.OrderDto;
import com.example.springapi.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
