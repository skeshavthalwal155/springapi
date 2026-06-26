package com.example.springapi.repositories;

import com.example.springapi.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte > {
}
