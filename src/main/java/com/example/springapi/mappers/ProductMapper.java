package com.example.springapi.mappers;

import com.example.springapi.dtos.ProductDto;
import com.example.springapi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toProductDto(Product product);
}
