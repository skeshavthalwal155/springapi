package com.example.springapi.repositories;

import com.example.springapi.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
